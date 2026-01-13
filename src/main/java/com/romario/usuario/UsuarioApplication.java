package com.romario.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableFeignClients
public class UsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioApplication.class, args);
	}

}
