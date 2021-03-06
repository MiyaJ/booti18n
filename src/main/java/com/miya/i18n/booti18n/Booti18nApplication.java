package com.miya.i18n.booti18n;

import com.miya.i18n.booti18n.common.MessageUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Booti18nApplication {

    public static void main(String[] args) {
        SpringApplication.run(Booti18nApplication.class, args);
        String message = MessageUtils.getMessage("mess.user.name");
        System.out.println("message{}" + message);
    }

}

