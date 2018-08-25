package com.youqel.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({ "classpath:/META-INF/spring/applicationContext.xml",
		"classpath:/META-INF/spring/database-flyway-migration.xml", "classpath:/META-INF/spring/database-props-createddl.xml" })
public class YouqelApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouqelApplication.class, args);
	}
}
