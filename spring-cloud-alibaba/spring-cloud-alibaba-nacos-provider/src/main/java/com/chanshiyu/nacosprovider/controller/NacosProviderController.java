package com.chanshiyu.nacosprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shiyu
 * @date 2019/9/25 11:18
 * @Description
 */
@RestController
public class NacosProviderController {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message) {
        String port = applicationContext.getEnvironment().getProperty("server.port");
        return String.format("Your message is %s and port is %s", message, port);
    }

    @GetMapping("/hi")
    public String hi() {
        String name = applicationContext.getEnvironment().getProperty("user.name");
        String age = applicationContext.getEnvironment().getProperty("user.age");
        return String.format("name: %s, age: %s", name, age);
    }

}
