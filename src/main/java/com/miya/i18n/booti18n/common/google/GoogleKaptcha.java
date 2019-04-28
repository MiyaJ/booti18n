package com.miya.i18n.booti18n.common.google;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoogleKaptcha implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7346000596572540128L;

	private String issuer = "OneRoot"; 	//服务名称
	
	private String secretKey;	//用户秘钥
}
