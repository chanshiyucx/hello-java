package com.chanshiyu.springcloudservicehello.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shiyu
 * @date 2019/9/23 14:20
 * @Description
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    private String port;

    @GetMapping("say")
    public String hello(String message) {
        return String.format("Your message is %s port %s", message, port);
    }

}
