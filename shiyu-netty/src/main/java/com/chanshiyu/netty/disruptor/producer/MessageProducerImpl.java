package com.chanshiyu.netty.disruptor.producer;

import com.chanshiyu.netty.disruptor.wapper.TranslatorDataWrapper;
import com.chanshiyu.netty.protocol.Packet;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author shiyu
 * @date 2019/9/12 15:04
 * @Description
 */
@Data
@AllArgsConstructor
public class MessageProducerImpl extends MessageProducer {

    private Integer commandId;
    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    /**
     * 发布事件
     * @param packet 应用包
     * @param ctx    上下文
     */
    public void publish(Packet packet, ChannelHandlerContext ctx) {
        super.publish(packet, ctx);
        // 取盘
        long sequence = ringBuffer.next();
        try {
            TranslatorDataWrapper wrapper = ringBuffer.get(sequence);
            wrapper.setPacket(packet);
            wrapper.setCtx(ctx);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
