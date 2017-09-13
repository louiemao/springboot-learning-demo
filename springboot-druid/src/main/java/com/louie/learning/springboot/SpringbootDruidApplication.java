package com.louie.learning.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@ServletComponentScan//用于扫描servlet和filter
public class SpringbootDruidApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDruidApplication.class, args);
	}
}
