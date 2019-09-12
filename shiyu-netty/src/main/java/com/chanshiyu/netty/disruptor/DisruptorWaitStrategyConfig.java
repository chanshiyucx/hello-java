package com.chanshiyu.netty.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shiyu
 * @date 2019/9/12 15:06
 * @Description
 */
@Configuration
public class DisruptorWaitStrategyConfig {

    @Bean
    @ConditionalOnMissingBean(WaitStrategy.class)
    public WaitStrategy getWaitStrategy() {
        // 如果 CPU 比较叼的话，可以用 YieldingWaitStrategy
        return new BlockingWaitStrategy();
    }

}
