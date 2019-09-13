package com.chanshiyu.netty.handler.request;

import com.chanshiyu.netty.disruptor.RingBufferWorkerPoolFactory;
import com.chanshiyu.netty.disruptor.producer.MessageProducer;
import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shiyu
 * @date 2019/9/12 15:23
 * @Description
 */
@Slf4j
public class DisruptorRequestHandler<T extends Packet> extends SimpleChannelInboundHandler<T> {

    /**
     * disruptor 工厂
     * @return 对象
     */
    public RingBufferWorkerPoolFactory getWorkerPoolFactory() {
        return SpringUtil.getBean(RingBufferWorkerPoolFactory.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        MessageProducer messageProducer = getWorkerPoolFactory().getMessageProducer(msg.getCommand());
        messageProducer.publish(msg, ctx);
    }

}
