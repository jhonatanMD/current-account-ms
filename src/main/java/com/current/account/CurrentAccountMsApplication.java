package com.current.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
public class CurrentAccountMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrentAccountMsApplication.class, args);
	}

}
