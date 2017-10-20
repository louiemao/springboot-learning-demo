package com.louie.learning.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/10/20.
 */
@RestController
public class TestController {

    @GetMapping("test")
    public String test(){
        return "test";
    }
}
