package com.chanshiyu.springcloudwebhellofeign.service.hystrix;

import com.chanshiyu.springcloudwebhellofeign.service.HelloService;
import org.springframework.stereotype.Component;

/**
 * @author shiyu
 * @date 2019/9/24 9:06
 * @Description
 */
@Component
public class HelloServiceHystrix implements HelloService {

    @Override
    public String sayHi(String message) {
        return String.format("Hi, your message is %s, but request error.", message);
    }
}
