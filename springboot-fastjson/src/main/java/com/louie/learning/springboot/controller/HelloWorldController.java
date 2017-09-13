package com.louie.learning.springboot.controller;

import com.louie.learning.springboot.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello,World!";
    }

    @RequestMapping("/getDemo")
    public User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setCreateTime(new Date());
        user.setRemarks("备注");
        return user;
    }
}
