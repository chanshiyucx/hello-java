package com.chanshiyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.chanshiyu.mapper")
public class ShiyuNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiyuNettyApplication.class, args);
    }

}
