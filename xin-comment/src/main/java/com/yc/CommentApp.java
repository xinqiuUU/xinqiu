package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommentApp {

	public static void main(String[] args) {
		System.out.println("xin-CommentApp服务启动成功");
		SpringApplication.run(CommentApp.class, args);
	}
}
