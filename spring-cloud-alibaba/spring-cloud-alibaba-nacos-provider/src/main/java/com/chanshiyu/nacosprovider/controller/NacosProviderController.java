package com.chanshiyu.nacosprovider.controller;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${server.port}")
    private String port;

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message) {
        return String.format("Your message is %s and port is %s", message, port);
    }

}
