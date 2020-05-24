package ink.carnation.seckill.common.config;

import ink.carnation.seckill.common.message.RedisMQConsumerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 桂乙侨
 * @Date 2020/5/22 20:28
 * @Version 1.0
 */
@Configuration
public class MessageQueueConfig {

    @Bean
    public RedisMQConsumerContainer redisMQConsumerContainer(){
        RedisMQConsumerContainer container = new RedisMQConsumerContainer();
        return container;
    }
}
