package com.chanshiyu.netty.disruptor.consumer;

import com.chanshiyu.netty.disruptor.wapper.TranslatorDataWrapper;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shiyu
 * @date 2019/9/12 15:10
 * @Description
 */
@Slf4j
public class MessageConsumer implements WorkHandler<TranslatorDataWrapper> {

    @Override
    public void onEvent(TranslatorDataWrapper wrapper) {
        // log.info("准备消费消息 -> {}", wrapper.getPacket().getCommand());
    }

}
