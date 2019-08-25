package com.chanshiyu.springvalidation.controller;

import com.chanshiyu.springvalidation.dataobject.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    @PostMapping("/live")
    public void live(@Valid @RequestBody User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("参数不正确，user={}, msg={}", user, bindingResult.getFieldError().getDefaultMessage());
        }
    }
}
