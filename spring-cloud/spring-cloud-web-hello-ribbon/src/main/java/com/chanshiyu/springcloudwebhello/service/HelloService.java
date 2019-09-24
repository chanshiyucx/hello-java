package com.chanshiyu.springcloudwebhello.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "sayError")
    public String sayHi(String message) {
        return restTemplate.getForObject("http://spring-cloud-service-hello/say?message=" + message, String.class);
    }

    public String sayError(String message) {
        return String.format("Hi，your message is : %s but request error.", message);
    }

}
