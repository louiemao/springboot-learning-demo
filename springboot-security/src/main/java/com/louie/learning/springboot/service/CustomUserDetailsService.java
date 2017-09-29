package com.louie.learning.springboot.service;

import com.louie.learning.springboot.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private Md5PasswordEncoder md5PasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if ("admin".equals(userName)) {
            UserEntity user = new UserEntity();
            user.setUsername(userName);
            user.setPassword(md5PasswordEncoder.encodePassword("admin",null));
            List<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority("admin"));
            user.setAuthorities(list);
            return user;
        } else {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
    }
}
