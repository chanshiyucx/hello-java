package com.chanshiyu.netty.protocol.request;

import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;

/**
 * @author shiyu
 * @date 2019/9/12 10:57
 * @Description
 */
public class HeartBeatRequestPacket extends Packet {

    @Override
    public Integer getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }

}
