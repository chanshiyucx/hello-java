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
    public Users queryUserForLogin(String username, String pwd);

    /** 注册用户 */
    public Users saveUser(Users user) throws Exception;

    /** 更新用户 */
    public Users updateUser(Users user) throws Exception;

    /** 根据用户名查询用户 */
    public Users queryUserByUsername(String username) throws Exception;

    /** 搜索好友 */
    public Integer searchFriend(SearchUser searchUser) throws Exception;

    /** 发送好友验证 */
    public void sendFriendRequest(SearchUser searchUser) throws Exception;

    /** 推荐好友 */
    public List<UsersVO> recommend();

    /** 查询好友申请 */
    public List<UsersVO> queryFriendRequestList(String userId);

    /** 删除好友申请记录 */
    public void deleteFriendRequest(String sendUserId, String acceptUserId);

    /** 通过好友申请 */
    public void passFriendRequest(String sendUserId, String acceptUserId);

    /** 获取好友列表 */
    public List<UsersVO> queryFriendList(String userId);

}
