package com.louie.learning.springboot.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.louie.learning.springboot.model.User;
import com.louie.learning.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userSerivce;

    @GetMapping("/findAll")
    public PageInfo<User> findAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        List<User> page = userSerivce.findAll(pageNum, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }

    @GetMapping("/findByPage")
    public PageInfo<User> findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        Page<User> page = userSerivce.findByPage(pageNum, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }
}
