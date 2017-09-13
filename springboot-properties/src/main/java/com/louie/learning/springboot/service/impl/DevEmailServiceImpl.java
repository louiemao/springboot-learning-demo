package com.louie.learning.springboot.service.impl;

import com.louie.learning.springboot.service.EmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")//开发环境的时候.
public class DevEmailServiceImpl implements EmailService {
    @Override
    public void send() {
        System.out.println("DevEmailServiceImpl.send().开发环境不执行邮件的发送.");
    }

}
