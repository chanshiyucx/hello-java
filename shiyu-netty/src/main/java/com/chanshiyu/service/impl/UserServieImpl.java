package com.chanshiyu.service.impl;

import com.chanshiyu.mapper.UsersMapper;
import com.chanshiyu.pojo.Users;
import com.chanshiyu.service.UserService;
import com.chanshiyu.util.MD5Utils;
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

    private final Sid sid;

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

    @Override
    public Users saveUser(Users user) throws Exception {
        String userId = sid.nextShort();
        user.setId(userId);
        user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        user.setNickname(user.getUsername());
        user.setAvatar("");
        user.setAvatarBig("");
        user.setQrcode("");
        usersMapper.insert(user);
        return user;
    }

}
