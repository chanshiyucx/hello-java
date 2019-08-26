package com.chanshiyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringToolApplication.class, args);
    }

}
