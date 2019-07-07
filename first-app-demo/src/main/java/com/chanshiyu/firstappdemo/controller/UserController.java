package com.chanshiyu.firstappdemo.controller;

import com.chanshiyu.firstappdemo.domain.User;
import com.chanshiyu.firstappdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserRepository userRepository;

    /**
     * 构造器注入优势：1. 不能修改 2. 提前进行初始化
     * @param userRepository 用户仓储
     */
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/person/save")
    public User save(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        if (userRepository.save(user)) {
            System.out.printf("用户: %s 保存成功 \n", user);
        }
        return user;
    }
}
