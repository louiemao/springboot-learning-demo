package com.louie.learning.springboot;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringbootFastjsonApplication extends WebMvcConfigurerAdapter{

	// 方法一：extends WebMvcConfigurerAdapter
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);

		//1、先定义一个convert转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		//2、添加fastjson的配置信息，比如是否要格式化返回的json数据；
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(
				//是否需要格式化
				SerializerFeature.PrettyFormat
		);
		//附加：处理中文乱码（后期添加）
		List<MediaType> fastMedisTypes=new ArrayList<MediaType>();
		fastMedisTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMedisTypes);
		//3、在convert中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);
		//4、将convert添加到converters
		converters.add(fastConverter);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootFastjsonApplication.class, args);
	}
}
