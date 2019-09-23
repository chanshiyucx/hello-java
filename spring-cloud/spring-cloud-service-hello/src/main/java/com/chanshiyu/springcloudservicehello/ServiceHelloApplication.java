package com.chanshiyu.springcloudservicehello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceHelloApplication.class, args);
    }

}
