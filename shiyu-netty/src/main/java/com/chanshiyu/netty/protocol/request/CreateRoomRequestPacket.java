package com.chanshiyu.netty.protocol.request;

import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shiyu
 * @date 2019/9/13 15:45
 * @Description
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateRoomRequestPacket extends Packet {

    private String name;

    private String users;

    private String createUserId;

    @Override
    public Integer getCommand() {
        return Command.CREATE_ROOM_REQUEST;
    }

}

