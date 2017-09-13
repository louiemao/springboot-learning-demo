package com.louie.learning.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {
    private static Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("/")
    public String sayHello() {
        logger.error("sayHello");
        return "Hello,World!";
    }
}
