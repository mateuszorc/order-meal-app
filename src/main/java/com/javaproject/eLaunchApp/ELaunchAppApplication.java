package com.javaproject.eLaunchApp;

import com.javaproject.eLaunchApp.DTO.PeriodDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ELaunchAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELaunchAppApplication.class, args);
	}

}
