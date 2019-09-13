package com.chanshiyu;

import com.chanshiyu.netty.disruptor.RingBufferWorkerPoolFactory;
import com.chanshiyu.netty.disruptor.consumer.MessageConsumer;
import com.chanshiyu.netty.disruptor.consumer.MessageConsumerImpl;
import com.chanshiyu.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.chanshiyu.mapper")
@ComponentScan(basePackages = {"com.chanshiyu", "org.n3r.idworker"})
@ServletComponentScan
@EnableScheduling
public class ShiyuNettyApplication {

    @Bean
    public SpringUtil getSpringUtil() {
        return new SpringUtil();
    }

    public static void main(String[] args) {

        SpringApplication.run(ShiyuNettyApplication.class, args);

        // 启动 disruptor
        MessageConsumer[] consumers = new MessageConsumer[8];
        for (int i = 0; i < consumers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl();
            consumers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory factory = SpringUtil.getBean(RingBufferWorkerPoolFactory.class);
        factory.initAndStart(consumers);
    }

}
