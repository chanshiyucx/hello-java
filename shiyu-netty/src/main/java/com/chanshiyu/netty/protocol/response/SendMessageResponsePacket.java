package com.chanshiyu.netty.protocol.response;

import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author shiyu
 * @date 2019/9/13 10:16
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SendMessageResponsePacket extends Packet {

    private int msgId;                  // 消息ID
    private int msgIndex;               // 消息序号，客户端用于检测消息是否到达
    private long date;

    @Override
    public Integer getCommand() {
        return Command.SEND_MESSAGE_RESPONSE;
    }

}
