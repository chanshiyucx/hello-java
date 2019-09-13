package com.chanshiyu.netty.protocol.command;

/**
 * @author shiyu
 * @date 2019/9/12 10:45
 * @Description: 消息命令字
 */
public interface Command {

    /**
     * 心跳包
     */
    int HEARTBEAT_REQUEST = 1;
    int HEARTBEAT_RESPONSE = 2;

    /**
     * 登陆
     */
    int LOGIN_REQUEST = 3;
    int LOGIN_RESPONSE = 4;

    /**
     * 消息
     */
    int MESSAGE_REQUEST = 5;
    int MESSAGE_RESPONSE = 6;

    /**
     * 消息发送成功
     */
    int MESSAGE_SUCCESS_RESPONSE = 7;

}
