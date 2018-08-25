package com.youqel.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ImportResource({"classpath*:applicationContext.xml"})
public class YouqelApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouqelApplication.class, args);
	}
}
