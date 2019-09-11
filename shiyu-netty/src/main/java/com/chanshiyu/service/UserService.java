package com.chanshiyu.service;

import com.chanshiyu.pojo.Users;
import com.chanshiyu.pojo.bo.SearchUser;

/**
 * @author shiyu
 * @date 2019/9/7 20:37
 * @Description
 */
public interface UserService {

    /** 查询用户名是否存在 */
    public boolean queryUsernameIsExist(String username);

    /** 查询用户是否存在 */
    public Users queryUserForLogin(String username, String pwd);

    /** 注册用户 */
    public Users saveUser(Users user) throws Exception;

    /** 更新用户 */
    public Users updateUser(Users user) throws Exception;

    /** 根据用户名查询用户 */
    public Users queryUserByUsername(String username) throws Exception;

    /** 搜索好友 */
    public Integer searchFriend(SearchUser searchUser) throws Exception;

}
