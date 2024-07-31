package com.micro.associacaoQueo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AssociacaoQueoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssociacaoQueoApplication.class, args);
	}

}
