package com.louie.learning.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/25.
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Model model){
//        model.addAttribute("name", "[Angel -- 守护天使]");
        model.addAttribute("name", null);
        model.addAttribute("gender",1);//gender:性别，1：男；0：女；

        List<Map<String,Object>> friends =new ArrayList<Map<String,Object>>();
        Map<String,Object> friend = new HashMap<String,Object>();
        friend.put("name", "张三");
        friend.put("age", 20);
        friends.add(friend);
        friend = new HashMap<String,Object>();
        friend.put("name", "李四");
        friend.put("age", 22);
        friends.add(friend);
        model.addAttribute("friends", friends);
        return "hello";
    }
}
