package com.louie.learning.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootSchedulingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootSchedulingApplication.class, args);
    }
}
