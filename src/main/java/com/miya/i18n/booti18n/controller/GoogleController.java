package com.miya.i18n.booti18n.controller;

import com.miya.i18n.booti18n.common.google.GoogleKaptcha;
import com.miya.i18n.booti18n.common.google.GoogleUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @DESC
 * @Author:Caixiaowei
 * @Date:2019/3/1
 * @Time:5:34 PM
 */
@RequestMapping("/gg")
@RestController
public class GoogleController {

    /**
     * 获取绑定google验证码 所要的用户信息
     *
     * @return
     * @author Mitnick2
     * @date 2018年9月27日
     */
    @GetMapping("/bindGoogleInfo")
    public Object getGGCodeMsg() {
        String userEmail = "540110865@qq.com";
        // 生成绑定验证码的基本信息
        Map<String, Object> ggMsg = GoogleUtils.createGoogleAuthQRCodeData(new GoogleKaptcha(), userEmail);
        System.out.println(ggMsg);
        return ggMsg;

        // 将生成的秘钥放到redis 中，用来校验最后绑定时秘钥的安全性
        //this.saveGGSecretKeyRedis((String) ggMsg.get("secretKey"));

    }
}
