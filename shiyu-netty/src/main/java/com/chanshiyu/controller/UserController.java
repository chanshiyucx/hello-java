package com.chanshiyu.controller;

import com.chanshiyu.enums.ApiStatusEnums;
import com.chanshiyu.enums.OperFriendRequestEnum;
import com.chanshiyu.pojo.Users;
import com.chanshiyu.pojo.bo.OperFriendRequest;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation(value = "用户详情", notes = "用户详情")
    @GetMapping("/detail")
    public CommJSONResult<UsersVO> detail(@ApiParam(value = "用户ID", required = true) String userId) throws Exception {
        Users user = userService.queryUserById(userId);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        return CommJSONResult.ok(usersVO);
    }

    @ApiOperation(value = "搜索好友", notes = "搜索好友")
    @PostMapping("/search")
    public CommJSONResult search(@ApiParam(value = "搜索用户", required = true) @Valid @RequestBody SearchUser bean) throws Exception {
        Integer status = userService.searchFriend(bean);
        if (!status.equals(ApiStatusEnums.SUCCESS.getStatus())) {
            return CommJSONResult.errorMsg(ApiStatusEnums.getMsgByKey(status));
        }

        Users result = userService.queryUserByUsername(bean.getFriendUserName());
        UsersVO friend = new UsersVO();
        BeanUtils.copyProperties(result, friend);
        return CommJSONResult.ok(friend);
    }

    @ApiOperation(value = "验证好友", notes = "验证好友")
    @PostMapping("/sendFriendRequest")
    public CommJSONResult sendFriendRequest(@ApiParam(value = "搜索用户", required = true) @Valid @RequestBody SearchUser bean) throws Exception {
        Integer status = userService.searchFriend(bean);
        if (!status.equals(ApiStatusEnums.SUCCESS.getStatus())) {
            return CommJSONResult.errorMsg(ApiStatusEnums.getMsgByKey(status));
        }
        userService.sendFriendRequest(bean);
        return CommJSONResult.ok();
    }

    @ApiOperation(value = "推荐用户", notes = "推荐用户")
    @GetMapping("/recommend")
    public CommJSONResult<List<UsersVO>> recommend() throws Exception {
        List<UsersVO> result = userService.recommend();
        return CommJSONResult.ok(result);
    }

    @ApiOperation(value = "好友申请", notes = "好友申请")
    @GetMapping("/friendRequest")
    public CommJSONResult<List<UsersVO>> friendRequest(@ApiParam(value = "用户ID", required = true) String userId) throws Exception {
        List<UsersVO> result = userService.queryFriendRequestList(userId);
        return CommJSONResult.ok(result);
    }

    @ApiOperation(value = "好友申请验证", notes = "好友申请验证")
    @PostMapping("/operFriendRequest")
    public CommJSONResult operFriendRequest(@ApiParam(value = "申请验证", required = true) @Valid @RequestBody OperFriendRequest bean) throws Exception {
        // 如果 operType 没有对应的枚举值，抛出错误
        if (StringUtils.isEmpty(OperFriendRequestEnum.getMsgByKey(bean.getOperType()))) {
            return CommJSONResult.errorMsg(ApiStatusEnums.PARAMS_ERROR.getMsg());
        }

        if (bean.getOperType().equals(OperFriendRequestEnum.IGNORE.getStatus())) {
            userService.deleteFriendRequest(bean.getSendUserId(), bean.getAcceptUserId());
        } else if (bean.getOperType().equals(OperFriendRequestEnum.PASS.getStatus())) {
            userService.passFriendRequest(bean.getSendUserId(), bean.getAcceptUserId());
        }

        return CommJSONResult.ok();
    }

    @ApiOperation(value = "好友列表", notes = "好友列表")
    @GetMapping("/friendList")
    public CommJSONResult<List<UsersVO>> friendList(@ApiParam(value = "用户ID", required = true) String userId) throws Exception {
        List<UsersVO> result = userService.queryFriendList(userId);
        return CommJSONResult.ok(result);
    }

}
