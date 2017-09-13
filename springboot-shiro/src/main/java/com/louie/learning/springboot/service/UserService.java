package com.louie.learning.springboot.service;

import com.louie.learning.springboot.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        if ("admin".equals(username)) {
            User user = new User();
            user.setId(1L);
            user.setUsername("admin");
            user.setPassword("d3c59d25033dbf980d29554025c23a75");
            user.setSalt("8d78869f470951332959580424d4bf4f");
            user.setLocked(false);
            return user;
        } else {
            return null;
        }
    }
}
