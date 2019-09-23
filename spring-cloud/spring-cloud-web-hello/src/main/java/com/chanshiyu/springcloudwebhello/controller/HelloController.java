package com.chanshiyu.springcloudwebhello.controller;

import com.chanshiyu.springcloudwebhello.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shiyu
 * @date 2019/9/23 16:22
 * @Description
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/say")
    public String sayHi(@RequestParam String message) {
        return helloService.sayHi(message);
    }

}
