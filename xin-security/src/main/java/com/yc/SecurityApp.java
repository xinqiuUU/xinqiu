package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApp {

	public static void main(String[] args) {
		System.out.println("xin-securityApp服务启动成功");
		SpringApplication.run(SecurityApp.class, args);
	}
}
