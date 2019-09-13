package com.chanshiyu.service;

import com.chanshiyu.pojo.Room;

import java.util.List;

/**
 * @author shiyu
 * @date 2019/9/13 10:31
 * @Description
 */
public interface RoomService {

    /** 查询房间是否存在 */
    public Room queryRoomByUsers(String users);

    /** 创建房间 */
    public Room createRoom(Room room) throws Exception;

}
