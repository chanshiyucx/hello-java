package com.chanshiyu.netty.protocol.message;

/**
 * @author shiyu
 * @date 2019/9/12 16:48
 * @Description
 */
public abstract class ChatMessage {

    /**
     * 消息类型
     */
    public abstract Byte getContentType();

}
