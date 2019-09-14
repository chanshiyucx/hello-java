package com.chanshiyu.netty.protocol.response;

import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author shiyu
 * @date 2019/9/13 15:52
 * @Description
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateRoomResponsePacket extends Packet {

    private String id;

    private String name;

    private String users;

    private String createUserId;

    private Date createTime;

    private String icon;

    /** 是否已存在 0不存在 1已存在 */
    private Integer isExist;

    @Override
    public Integer getCommand() {
        return Command.CREATE_ROOM_RESPONSE;
    }

}
