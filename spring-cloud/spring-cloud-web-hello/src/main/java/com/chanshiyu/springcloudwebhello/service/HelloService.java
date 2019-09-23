package com.chanshiyu.springcloudwebhello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author shiyu
 * @date 2019/9/23 16:21
 * @Description
 */
@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    public String sayHi(String message) {
        return restTemplate.getForObject("http://spring-cloud-service-hello/say?message=" + message, String.class);
    }

}
