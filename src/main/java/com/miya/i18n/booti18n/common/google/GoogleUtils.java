package com.miya.i18n.booti18n.common.google;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GoogleUtils {

	/**
	 * 生成用户绑定google验证码所需要的信息。 Google Authenticator 二维码所需信息 Google Authenticator
	 * 约定的二维码信息格式 :
	 * otpauth://totp/{issuer}:{account}?secret={secret}&issuer={issuer} 参数需要 url 编码
	 * + 号需要替换成 %20
	 * 
	 * @param secret,密钥
	 *            使用 createSecretKey 方法生成
	 * @param account,用户账户
	 *            如: example@domain.com 138XXXXXXXX
	 * @param issuer,服务名称
	 *            如: Google Github 印象笔记
	 */
	public static Map<String, Object> createGoogleAuthQRCodeData(GoogleKaptcha googleKaptcha, String email) {
		Map<String, Object> returnMap = new HashMap<>();
		StringBuffer account = new StringBuffer();
		String secret = GoogleUtils.createSecretKey();
		if (StringUtils.isNotBlank(email)) {
			account.append(email);
		}
		String issuer = googleKaptcha.getIssuer();

		String qrCodeData = "otpauth://totp/%s?secret=%s&issuer=%s";
		try {
			String content = String.format(qrCodeData,
					URLEncoder.encode(issuer + ":" + account.toString(), "UTF-8").replace("+", "%20"),
					URLEncoder.encode(secret, "UTF-8").replace("+", "%20"),
					URLEncoder.encode(issuer, "UTF-8").replace("+", "%20"));

			returnMap.put("secretKey", secret);

			returnMap.put("content", content);
			return returnMap;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> createGoogleAuthQRCodeData(GoogleKaptcha googleKaptcha, String email,
                                                                 String acc) {
		Map<String, Object> returnMap = new HashMap<>();
		StringBuffer account = new StringBuffer();
		String secret = GoogleUtils.createSecretKey();
		if (StringUtils.isNotBlank(email)) {
			account.append(email).append("-").append(acc);
		}
		String issuer = googleKaptcha.getIssuer();

		String qrCodeData = "otpauth://totp/%s?secret=%s&issuer=%s";
		try {
			String content = String.format(qrCodeData,
					URLEncoder.encode(issuer + ":" + account.toString(), "UTF-8").replace("+", "%20"),
					URLEncoder.encode(secret, "UTF-8").replace("+", "%20"),
					URLEncoder.encode(issuer, "UTF-8").replace("+", "%20"));

			returnMap.put("secretKey", secret);

			returnMap.put("content", content);
			return returnMap;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 随机生成一个google密钥
	 */
	public static String createSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[10];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		String secretKey = base32.encodeToString(bytes);
		return secretKey.toLowerCase();
	}

	/** 时间前后偏移量 */
	private static final int timeExcursion = 8;

	/**
	 * 校验google验证码是否正确
	 * 
	 * @author blank
	 * @date 2018年6月14日
	 * @param userSecretKey,用户秘钥
	 * @param code，验证码
	 * @return
	 */
	public static boolean validateCode(String userSecretKey, String code) {
		long time = System.currentTimeMillis() / 1000 / 30;
		log.info("当前系统时间戳：{}", System.currentTimeMillis());
		for (int i = -timeExcursion; i <= timeExcursion; i++) {
			String totp = getTOTP(userSecretKey, time);
			if (totp.equals(code)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 根据密钥获取验证码 返回字符串是因为验证码有可能以 0 开头
	 * 
	 * @param secretKey
	 *            密钥
	 * @param time
	 *            第几个 30 秒 System.currentTimeMillis() / 1000 / 30
	 */
	public static String getTOTP(String secretKey, long time) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey.toUpperCase());
		String hexKey = Hex.encodeHexString(bytes);
		String hexTime = Long.toHexString(time);
		return TOTP.generateTOTP(hexKey, hexTime, "6");
	}

	public static void main(String[] args) {
		GoogleKaptcha gk = new GoogleKaptcha();
		gk.setIssuer("OneRoot");
		gk.setSecretKey("oqdexjiqnem4skpo");
		String acc = "WalletAdmin3";
		String email = "yaoya_2004@163.com";
		Map<String, Object> createGoogleAuthQRCodeData = GoogleUtils.createGoogleAuthQRCodeData(gk, email, acc);
		System.out.println(">>" + createGoogleAuthQRCodeData);
	}

}
