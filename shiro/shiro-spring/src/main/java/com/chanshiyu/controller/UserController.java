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
            // 设置是否记住登录
            token.setRememberMe(true);
            subject.login(token);
        } catch (Exception e) {
            return e.getMessage();
        }

        if (subject.hasRole("admin")) {
            return "有 admin 权限";
        }

        return "登录成功！";
    }

    @RequestMapping(value="/testRoles", method=RequestMethod.GET)
    @ResponseBody
    public String testRole() {
        return "test roles success";
    }

    @RequestMapping(value="/testRoles1", method=RequestMethod.GET)
    @ResponseBody
    public String testRole1() {
        return "test roles success";
    }

    @RequestMapping(value="/testRolesOr", method=RequestMethod.GET)
    @ResponseBody
    public String testRolesOr() {
        return "test testRolesOr success";
    }

    @RequestMapping(value="/testPerms", method=RequestMethod.GET)
    @ResponseBody
    public String testPerms() {
        return "test permissions success";
    }

    @RequestMapping(value="/testPerms1", method=RequestMethod.GET)
    @ResponseBody
    public String testPerms1() {
        return "test permissions1 success";
    }
}
