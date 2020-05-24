package ink.carnation.seckill.common.message.impl;

import ink.carnation.seckill.common.message.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author 桂乙侨
 * @Date 2020/5/21 16:54
 * @Version 1.0
 */
@Component
@Slf4j
public class OrderProducer implements Producer {

    @Autowired
    private RedisTemplate redisTemplate;


    public OrderProducer(){

    }

    @Override
    public void produce(String queue,Object message) {
        redisTemplate.opsForList().leftPush(queue,message);
        log.info("添加消息到队列:{}",message.toString());
    }
}
