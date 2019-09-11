package com.chanshiyu.mapper;

import com.chanshiyu.pojo.Users;
import com.chanshiyu.pojo.vo.UsersVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UsersMapper extends Mapper<Users> {

    List<UsersVO> queryFriendRequestList(@Param("acceptUserId") String acceptUserId);

    List<UsersVO> queryFriendList(@Param("myUserId") String myUserId);

}