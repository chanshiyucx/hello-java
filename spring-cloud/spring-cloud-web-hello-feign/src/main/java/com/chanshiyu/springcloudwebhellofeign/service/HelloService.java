package com.chanshiyu.springcloudwebhellofeign.service;

import com.chanshiyu.springcloudwebhellofeign.service.hystrix.HelloServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shiyu
 * @date 2019/9/23 19:17
 * @Description
 */
@FeignClient(value = "spring-cloud-service-hello", fallback = HelloServiceHystrix.class)
public interface HelloService {

    @GetMapping("say")
    public String sayHi(@RequestParam(value = "message") String message);

}
