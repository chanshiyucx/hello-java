package com.chanshiyu.controller;

import com.chanshiyu.pojo.Users;
import com.chanshiyu.service.UserService;
import com.chanshiyu.util.CommJSONResult;
import com.chanshiyu.util.MD5Utils;
import com.chanshiyu.vo.UsersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author shiyu
 * @date 2019/9/7 20:27
 * @Description 用户相关 controller
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理", tags = {"用户管理Controller"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PostMapping("/registerOrlogin")
    public CommJSONResult<UsersVO> registerOrlogin(@ApiParam(value = "登录或注册", required = true) @Valid @RequestBody Users bean) throws Exception {
        boolean usernameIsExist = userService.queryUsernameIsExist(bean.getUsername());
        Users userResult = null;
        if (usernameIsExist) {
            // 登录
            userResult = userService.queryUserForLogin(bean.getUsername(), MD5Utils.getMD5Str(bean.getPassword()));
            if (userResult == null) {
                return CommJSONResult.errorMsg("用户名或密码错误");
            }
        } else {
            // 注册
            userResult = userService.saveUser(bean);
        }

        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(userResult, userVO);
        return CommJSONResult.ok(userVO);
    }

}