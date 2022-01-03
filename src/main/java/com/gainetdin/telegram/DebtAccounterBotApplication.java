package com.gainetdin.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DebtAccounterBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebtAccounterBotApplication.class, args);
	}

}