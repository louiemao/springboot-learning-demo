package com.louie.learning.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SpringbootUeditorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootUeditorApplication.class, args);
	}
}
