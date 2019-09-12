package com.chanshiyu.netty.disruptor;

import com.chanshiyu.netty.disruptor.consumer.MessageConsumer;
import com.chanshiyu.netty.disruptor.producer.MessageProducer;
import com.chanshiyu.netty.disruptor.producer.MessageProducerImpl;
import com.chanshiyu.netty.disruptor.wapper.TranslatorDataWrapper;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * @author shiyu
 * @date 2019/9/12 14:59
 * @Description
 */
@Slf4j
@Component
public class RingBufferWorkerPoolFactory {

    @Value("${disruptor.buffer.size}")
    private int mBufferSize;

    @Autowired
    private WaitStrategy mWaitStrategy;

    private Map<Integer, MessageProducer> producers = new ConcurrentHashMap<>();

    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    public void initAndStart(MessageConsumer[] messageConsumers) {
        //1. 构建ringBuffer对象
        this.ringBuffer = RingBuffer.create(ProducerType.MULTI,
                TranslatorDataWrapper::new,
                mBufferSize,
                mWaitStrategy);
        //2.通过 ringBuffer 创建一个屏障
        SequenceBarrier sequenceBarrier = this.ringBuffer.newBarrier();
        //3.创建多个消费者数组
        WorkerPool<TranslatorDataWrapper> workerPool = new WorkerPool<>(
                this.ringBuffer,
                sequenceBarrier,
                new EventExceptionHandler(), messageConsumers);
        //4 设置多个消费者的sequence序号 用于单独统计消费进度, 并且设置到ringbuffer中
        this.ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        //5 启动我们的工作池
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2));
    }

    public MessageProducer getMessageProducer(Integer commandId) {
        MessageProducer messageProducer = producers.get(commandId);
        if (messageProducer == null) {
            messageProducer = new MessageProducerImpl(commandId, this.ringBuffer);
            producers.put(commandId, messageProducer);
        }
        return messageProducer;
    }

    /**
     * 异常静态类
     * @author Alienware
     */
    static class EventExceptionHandler implements ExceptionHandler<TranslatorDataWrapper> {
        public void handleEventException(Throwable ex, long sequence, TranslatorDataWrapper event) {
            log.error("handleEventException -> ex:{}  sequence:{} event:{}", ex.getMessage(), sequence, event.getClass().toString());
            ex.printStackTrace();
        }

        public void handleOnStartException(Throwable ex) {
            log.error("handleOnStartException -> ex:{}", ex.getMessage());
            ex.printStackTrace();
        }

        public void handleOnShutdownException(Throwable ex) {
            log.error("handleOnShutdownException -> ex:{} ", ex.getMessage());
            ex.printStackTrace();
        }
    }

}
