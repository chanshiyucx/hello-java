package com.chanshiyu.controller;

import com.chanshiyu.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @RequestMapping(value = "/subLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(User user) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        try {
            subject.login(token);
        } catch (Exception e) {
            return e.getMessage();
        }

        if (subject.hasRole("admin")) {
            return "有 admin 权限";
        }

        return "登录成功！";
    }
}
