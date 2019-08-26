package com.chanshiyu.controller;

import com.chanshiyu.dataobject.User;
import com.chanshiyu.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chanshiyu
 * @Description: 用户相关接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUser")
    public ResultVO<User> getUser() {
        User user = new User("shiyu", 18);
        return ResultVO.ok(user);
    }

}
