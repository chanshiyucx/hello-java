package com.chanshiyu.firstappdemo.config;

import com.chanshiyu.firstappdemo.domain.User;
import com.chanshiyu.firstappdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Configuration
public class RouterFunctionConfiguration {

    /**
     * Servlet
     * 请求接口：ServletRequest 或者 HttpServletRequest
     * 响应接口：ServletResponse 或者 HttpServletRequest
     * Spring 5.0 重新定义了请求接口和响应接口
     * 请求接口：ServerRequest
     * 响应接口：ServerResponse
     * 既可支持 Servlet 规范，也可以支持自定义，比如 Netty
     *
     * 四种依赖注入方式：1.构造器注入 2.方法参数注入 3.字段注入 4.set注入
     * 这里使用方法参数注入
     */
    @Bean
    @Autowired
    public RouterFunction<ServerResponse> personFindAll(UserRepository userRepository) {
        return RouterFunctions.route(RequestPredicates.GET("/person/find/all"),
                request -> {
                    Collection<User> users = userRepository.findAll();
                    Flux<User> userFlux = Flux.fromIterable(users);
                    return ServerResponse.ok().body(userFlux, User.class);
                });
    }
}
