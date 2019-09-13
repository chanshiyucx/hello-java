package com.chanshiyu.netty.protocol.response;

import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author shiyu
 * @date 2019/9/13 15:20
 * @Description
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginResponsePacket extends Packet {

    private String userId;

    private String username;

    private String nickname;

    private boolean success;

    private String msg;

    @Override
    public Integer getCommand() {
        return Command.LOGIN_RESPONSE;
    }

}
