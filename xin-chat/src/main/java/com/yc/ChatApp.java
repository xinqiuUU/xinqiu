package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApp {

	public static void main(String[] args) {
		System.out.println("xin-ChatApp服务启动成功");
		SpringApplication.run(ChatApp.class, args);
	}
}
