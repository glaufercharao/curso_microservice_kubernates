package com.gfstechnology.msvcstudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcStudentApplication.class, args);
	}

}
