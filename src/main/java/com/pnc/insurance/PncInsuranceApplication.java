package com.pnc.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PncInsuranceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PncInsuranceApplication.class, args);
	}

}
