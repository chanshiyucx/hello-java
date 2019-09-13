package com.chanshiyu.netty.protocol.request;

import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shiyu
 * @date 2019/9/13 14:42
 * @Description
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String nickname;

    @Override
    public Integer getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
