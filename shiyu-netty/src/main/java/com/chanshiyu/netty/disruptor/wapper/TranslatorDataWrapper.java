package com.chanshiyu.netty.disruptor.wapper;

import com.chanshiyu.netty.protocol.Packet;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

/**
 * @author shiyu
 * @date 2019/9/12 15:03
 * @Description: 包裹传输的数据
 */
@Data
public class TranslatorDataWrapper {

    private Packet packet;

    private ChannelHandlerContext ctx;

}
