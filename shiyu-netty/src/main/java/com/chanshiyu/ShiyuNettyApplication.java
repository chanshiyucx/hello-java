package com.chanshiyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.chanshiyu.mapper")
@ComponentScan(basePackages = {"com.chanshiyu", "org.n3r.idworker"})
public class ShiyuNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiyuNettyApplication.class, args);
    }

}
