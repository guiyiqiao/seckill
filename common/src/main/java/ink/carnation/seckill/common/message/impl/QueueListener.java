package ink.carnation.seckill.common.message.impl;

import ink.carnation.seckill.common.message.Consumer;
import ink.carnation.seckill.common.message.RedisMQConsumerContainer;
import ink.carnation.seckill.common.model.message.OrderMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author 桂乙侨
 * @Date 2020/5/22 19:42
 * @Version 1.0
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class QueueListener implements Runnable {

    private RedisTemplate redisTemplate;

    private String queue;

    private Consumer consumer;

    @Override
    public void run() {
        log.info("QueueListener start...queue:{}", queue);

        while (RedisMQConsumerContainer.RUNNING){
            Object message = redisTemplate.opsForList().rightPop(queue,500,TimeUnit.MICROSECONDS);
            if( message instanceof OrderMessage){
                consumer.consume(message);
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
