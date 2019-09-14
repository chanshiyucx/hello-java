package com.chanshiyu.netty.protocol.request;

import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shiyu
 * @date 2019/9/12 18:37
 * @Description
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SendMessageRequestPacket extends Packet {

    private int msgIndex;               // 消息序列
    private String roomId;              // 房间Id
    private String msg;                 // 消息内容

    @Override
    public Integer getCommand() {
        return Command.SEND_MESSAGE_REQUEST;
    }

}
