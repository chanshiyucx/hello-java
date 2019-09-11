package com.chanshiyu.service.impl;

import com.chanshiyu.enums.ApiStatusEnums;
import com.chanshiyu.mapper.MyFriendsMapper;
import com.chanshiyu.mapper.UsersMapper;
import com.chanshiyu.pojo.MyFriends;
import com.chanshiyu.pojo.Users;
import com.chanshiyu.pojo.bo.SearchUser;
import com.chanshiyu.service.UserService;
import com.chanshiyu.util.MD5Utils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @author shiyu
 * @date 2019/9/7 20:38
 * @Description 用户Service
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServieImpl implements UserService {

    private final UsersMapper usersMapper;

    private final MyFriendsMapper myFriendsMapper;

    private final Sid sid;

    private final String NICKNAME = "時語-";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);
        Users result = usersMapper.selectOne(user);
        return result != null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String pwd) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", pwd);

        /*Example example = new Example.Builder(Users.class)
                .where(Sqls.custom()
                        .andEqualTo("username", username)
                        .andEqualTo("password", pwd))
                .build();*/

        Users result = usersMapper.selectOneByExample(example);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users saveUser(Users user) throws Exception {
        String userId = sid.nextShort();
        user.setId(userId);
        user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        user.setNickname(NICKNAME + user.getUsername());
        user.setAvatar("");
        user.setAvatarBig("");
        usersMapper.insert(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUser(Users user) throws Exception {
        usersMapper.updateByPrimaryKeySelective(user);
        return queryUserById(user.getId());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer searchFriend(SearchUser searchUser) throws Exception {
        // 搜索的用户是否存在
        Users user = queryUserByUsername(searchUser.getFriendUserName());
        if (user == null) {
            return ApiStatusEnums.FRIEND_NOT_NULL.getStatus();
        }

        // 搜索的用户是否为自己
        if (user.getId().equals(searchUser.getMyUserId())) {
            return ApiStatusEnums.FRIEND_NOT_SELF.getStatus();
        }

        // 搜索的用户是否已经是好友
        Example example = new Example(MyFriends.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("myUserId", searchUser.getMyUserId());
        criteria.andEqualTo("myFriendUserId", user.getId());
        MyFriends friend = myFriendsMapper.selectOneByExample(example);
        if (friend != null) {
            return ApiStatusEnums.FRIEND_ON_LIST.getStatus();
        }

        return ApiStatusEnums.SUCCESS.getStatus();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserByUsername(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        return usersMapper.selectOneByExample(example);
    }

    private Users queryUserById(String id) {
        return usersMapper.selectByPrimaryKey(id);
    }

}
