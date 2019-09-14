package com.chanshiyu.service;

import com.chanshiyu.pojo.Users;
import com.chanshiyu.pojo.bo.SearchUser;
import com.chanshiyu.pojo.vo.UsersVO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author shiyu
 * @date 2019/9/7 20:37
 * @Description
 */
public interface UserService {

    /** 查询用户名是否存在 */
    public boolean queryUsernameIsExist(String username);

    /** 查询用户是否存在 */
    Users queryUserForLogin(String username, String pwd);

    /** 注册用户 */
    Users saveUser(Users user) throws Exception;

    /** 更新用户 */
    Users updateUser(Users user) throws Exception;

    /** 根据用户名查询用户 */
    Users queryUserByUsername(String username) throws Exception;

    /** 根据用户ID查询用户 */
    Users queryUserById(String userId) throws Exception;

    /** 搜索好友 */
    Integer searchFriend(SearchUser searchUser) throws Exception;

    /** 发送好友验证 */
    void sendFriendRequest(SearchUser searchUser) throws Exception;

    /** 推荐好友 */
    List<UsersVO> recommend();

    /** 查询好友申请 */
    List<UsersVO> queryFriendRequestList(String userId);

    /** 删除好友申请记录 */
    void deleteFriendRequest(String sendUserId, String acceptUserId) throws Exception;

    /** 通过好友申请 */
    void passFriendRequest(String sendUserId, String acceptUserId) throws Exception;

    /** 获取好友列表 */
    List<UsersVO> queryFriendList(String userId);

    /** 删除好友 */
    void deleteMyFriend(String userId, String friendUserId) throws Exception ;

}
