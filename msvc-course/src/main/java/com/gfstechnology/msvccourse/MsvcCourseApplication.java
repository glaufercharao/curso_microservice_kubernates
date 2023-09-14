package com.gfstechnology.msvccourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class MsvcCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcCourseApplication.class, args);
    }

}
