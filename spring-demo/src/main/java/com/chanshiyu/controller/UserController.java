package com.chanshiyu.controller;

import com.chanshiyu.dataobject.User;
import com.chanshiyu.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chanshiyu
 * @description 用户相关接口
 */
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final RedisTemplate redisTemplate;

    @GetMapping("/getUser")
    public ResultVO<User> getUser() {
        String key = "shiyu";
        redisTemplate.opsForValue().set(key, "chanshiyu.com");
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println("获取缓存中key为" + key + "的值为：" + value);

        User user = new User("shiyu", 18);
        String userKey = "chan";
        redisTemplate.opsForValue().set(userKey, user);
        User newUser = (User) redisTemplate.opsForValue().get(userKey);
        System.out.println("获取缓存中key为" + userKey + "的值为：" + newUser);

        return ResultVO.ok(user);
    }

}
