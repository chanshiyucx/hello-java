package com.chanshiyu.netty.protocol;

import com.chanshiyu.netty.serialize.SerializerAlgorithm;
import lombok.Data;

/**
 * @author shiyu
 * @date 2019/9/12 10:53
 * @Description: 消息包抽象类
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    private Byte algorithm = SerializerAlgorithm.JSON;

    public abstract Integer getCommand();

}
