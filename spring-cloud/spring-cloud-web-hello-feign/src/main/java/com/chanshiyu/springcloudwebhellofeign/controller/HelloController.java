package com.chanshiyu.springcloudwebhellofeign.controller;

import com.chanshiyu.springcloudwebhellofeign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shiyu
 * @date 2019/9/23 19:19
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
