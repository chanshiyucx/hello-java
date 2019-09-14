package com.chanshiyu.netty.protocol.response;

import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author shiyu
 * @date 2019/9/12 16:50
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageResponsePacket extends Packet {

    private int msgId;                  // 消息ID
    private String roomId;              // 房间id
    private String sendUserId;          // 发送者id
    private String sendUserAvatar;      // 发送者头像
    private String msg;                 // 消息内容 json
    private long date;                  // 消息时间

    @Override
    public Integer getCommand() {
        return Command.MESSAGE_RESPONSE;
    }

}
