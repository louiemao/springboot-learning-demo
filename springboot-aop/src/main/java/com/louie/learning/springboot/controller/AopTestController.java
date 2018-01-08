package com.louie.learning.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/8.
 */
@RestController
@RequestMapping("/aop")
public class AopTestController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }

    @GetMapping("/test")
    public String test(String param1) {
        return "Hello,World!" + param1;
    }

    @PostMapping("/test")
    public String test2(String param1, String param2) {
        return "Hello,World!" + param1 + param2;
    }

    @RequestMapping("/testAfterReturning.do")
    public String testAfterReturning(String key){

        return "key=: "+key;
    }
    @RequestMapping("/testAfterReturning01.do")
    public Integer testAfterReturning01(Integer key){

        return key;
    }
    @RequestMapping("/testAfterThrowing.do")
    public String testAfterThrowing(String key){

        throw new NullPointerException();
    }
    @RequestMapping("/testAfter.do")
    public String testAfter(String key){

        throw new NullPointerException();
    }
    @RequestMapping("/testAfter02.do")
    public String testAfter02(String key){

        return key;
    }
    @RequestMapping("/testAroundService.do")
    public String testAroundService(String key){

        return "环绕通知："+key;
    }
}
