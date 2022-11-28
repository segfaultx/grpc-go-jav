package com.example.grpcjavago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GprctestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GprctestApplication.class, args);
	}

}
