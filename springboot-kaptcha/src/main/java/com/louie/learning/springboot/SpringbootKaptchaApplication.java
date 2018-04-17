package com.louie.learning.springboot;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class SpringbootKaptchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKaptchaApplication.class, args);
	}

	@Bean
	public DefaultKaptcha captchaProducer(){
		DefaultKaptcha captchaProducer =new DefaultKaptcha();
		Properties properties =new Properties();
		properties.setProperty("kaptcha.border","yes");
		properties.setProperty("kaptcha.border.color","105,179,90");
		properties.setProperty("kaptcha.textproducer.font.color","blue");
		properties.setProperty("kaptcha.image.width","125");
		properties.setProperty("kaptcha.image.height","45");
		properties.setProperty("kaptcha.textproducer.font.size","45");
		properties.setProperty("kaptcha.session.key","code");
		properties.setProperty("kaptcha.textproducer.char.length","4");
		properties.setProperty("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
		Config config=new Config(properties);
		captchaProducer.setConfig(config);
		return  captchaProducer;
	}
}
