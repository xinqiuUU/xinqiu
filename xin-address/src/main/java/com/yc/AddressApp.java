package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync  // 开启异步
@SpringBootApplication
public class AddressApp {

	public static void main(String[] args) {
		System.out.println("xin-addressApp服务启动成功");
		SpringApplication.run(AddressApp.class, args);
	}
}
