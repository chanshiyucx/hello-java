package com.chanshiyu.netty.protocol.response;

import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;

/**
 * @author shiyu
 * @date 2019/9/12 10:58
 * @Description
 */
public class HeartBeatResponsePacket extends Packet {

    @Override
    public Integer getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }

}
