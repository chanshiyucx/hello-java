package com.chanshiyu.service;

import com.chanshiyu.pojo.Room;

/**
 * @author shiyu
 * @date 2019/9/13 16:51
 * @Description
 */
public interface RoomService {

    /** 查询房间是否存在 */
    public Room queryRoomByUsers(String users);

    /** 创建房间 */
    public Room createRoom(Room room) throws Exception;

}
