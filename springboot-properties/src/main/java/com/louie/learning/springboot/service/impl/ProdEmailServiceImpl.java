package com.louie.learning.springboot.service.impl;

import com.louie.learning.springboot.service.EmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")//生产环境的时候.
public class ProdEmailServiceImpl implements EmailService {
    @Override
    public void send() {
        System.out.println("ProdEmailServiceImpl.send().生产环境执行邮件的发送.");
        //具体的邮件发送代码.
        //mail.send();
    }

}
