package com.chanshiyu.nacosprovider.controller;

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

    @GetMapping("/say/{message}")
    public String say(@PathVariable String message) {
        return String.format("Your message is %s", message);
    }

}
