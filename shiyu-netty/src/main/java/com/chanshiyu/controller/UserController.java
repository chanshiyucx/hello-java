package com.chanshiyu.controller;

import com.chanshiyu.enums.ApiStatusEnums;
import com.chanshiyu.pojo.Users;
import com.chanshiyu.pojo.bo.SearchUser;
import com.chanshiyu.pojo.vo.UsersVO;
import com.chanshiyu.service.UserService;
import com.chanshiyu.util.CommJSONResult;
import com.chanshiyu.util.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
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

    @ApiOperation(value = "用户登录注册", notes = "登录或注册")
    @PostMapping("/registerOrlogin")
    public CommJSONResult<UsersVO> registerOrlogin(@ApiParam(value = "登录或注册", required = true) @Valid @RequestBody Users bean) throws Exception {
        boolean usernameIsExist = userService.queryUsernameIsExist(bean.getUsername());
        Users userResult = null;
        if (usernameIsExist) {
            // 登录
            userResult = userService.queryUserForLogin(bean.getUsername(), MD5Utils.getMD5Str(bean.getPassword()));
            if (userResult == null) {
                return CommJSONResult.errorMsg(ApiStatusEnums.USERNAME_PASSWORD_NOT_EMPTY.getMsg());
            }
        } else {
            // 注册
            userResult = userService.saveUser(bean);
        }

        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(userResult, userVO);
        return CommJSONResult.ok(userVO);
    }

    @ApiOperation(value = "用户更新", notes = "用户更新")
    @PostMapping("/update")
    public CommJSONResult<Users> update(@ApiParam(value = "更新用户", required = true) @Valid @RequestBody UsersVO bean) throws Exception {
        Users user = new Users();
        BeanUtils.copyProperties(bean, user);
        Users result = userService.updateUser(user);
        return CommJSONResult.ok(result);
    }

    @ApiOperation(value = "搜索好友", notes = "搜索好友")
    @PostMapping("/search")
    public CommJSONResult search(@ApiParam(value = "搜索用户", required = true) @Valid @RequestBody SearchUser bean) throws Exception {
        Integer status = userService.searchFriend(bean);
        if (!status.equals(ApiStatusEnums.SUCCESS.getStatus())) {
            return CommJSONResult.errorMsg(ApiStatusEnums.getMsgByKey(status));
        }

        Users friend = userService.queryUserByUsername(bean.getFriendUserName());
        return CommJSONResult.ok(friend);
    }

}
