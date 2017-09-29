package com.louie.learning.springboot;

import com.louie.learning.springboot.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//配置安全策略
                .antMatchers("/", "/home").permitAll()//定义/请求不需要验证
                .anyRequest().authenticated()//其余的所有请求都需要验证
                .and()
                .formLogin()//使用form表单登录
                .loginPage("/login") //指定登录页是"/login"
                .defaultSuccessUrl("/hello")//登录成功后默认跳转到"/hello"
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //inMemoryAuthentication 从内存中获取
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");


        //jdbcAuthentication从数据库中获取，但是默认是以security提供的表结构
        //usersByUsernameQuery 指定查询用户SQL
        //authoritiesByUsernameQuery 指定查询权限SQL
        //auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query).authoritiesByUsernameQuery(query);

        //注入userDetailsService，需要实现userDetailsService接口
        auth.userDetailsService(customUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 设置用户密码的加密方式为MD5加密
     *
     * @return
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        return md5PasswordEncoder;
    }

    /**
     * 自定义UserDetailsService，从数据库中读取用户信息
     *
     * @return
     */
    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }
}