package com.elibrary.backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElibraryBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElibraryBackendApplication.class, args);
	}

	// Bean to provide a ModelMapper for object mapping
	@Bean
	public ModelMapper modelMapper(){
		return  new ModelMapper();
	}

}
