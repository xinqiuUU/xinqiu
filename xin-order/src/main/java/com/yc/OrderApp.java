package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderApp {

	public static void main(String[] args) {
		System.out.println("xin-OrderApp服务启动成功");
		SpringApplication.run(OrderApp.class, args);
	}
}
