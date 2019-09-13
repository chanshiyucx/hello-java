package com.chanshiyu.service.impl;

import com.chanshiyu.mapper.RoomMapper;
import com.chanshiyu.pojo.Room;
import com.chanshiyu.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author shiyu
 * @date 2019/9/13 10:34
 * @Description
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoomServiceImpl implements RoomService {

    private final RoomMapper roomMapper;

    private final Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Room queryRoomByUsers(String users) {
        Example example = new Example(Room.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("users", users);
        return roomMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Room createRoom(Room room) throws Exception {
        room.setId(sid.nextShort());
        room.setCreateTime(new Date());
        roomMapper.insert(room);
        return room;
    }
}
