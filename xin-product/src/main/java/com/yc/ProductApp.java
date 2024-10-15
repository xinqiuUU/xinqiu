package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync  // 开启异步
@SpringBootApplication
public class ProductApp {

	public static void main(String[] args) {
		System.out.println("xin-productApp服务启动成功");
		SpringApplication.run(ProductApp.class, args);
	}
}
