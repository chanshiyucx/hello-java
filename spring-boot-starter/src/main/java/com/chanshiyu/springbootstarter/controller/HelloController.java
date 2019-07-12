package com.chanshiyu.springbootstarter.controller;

import com.chanshiyu.springbootstarter.pojo.JSONResult;
import com.chanshiyu.springbootstarter.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

// RestController  包括 ResponseBody和Controller两个注解
@RestController
@RequestMapping("/user")
public class HelloController {

    @RequestMapping("/getUser")
    public Object hello() {
        User user = new User();
        user.setName("chanshiyu");
        user.setAge(24);
        user.setPassword("hello");
        user.setBirthday(new Date());

        return JSONResult.ok(user);
    }
}
