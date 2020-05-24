package ink.carnation.seckill.common.message;

import ink.carnation.seckill.common.message.impl.QueueListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 桂乙侨
 * @Date 2020/5/22 20:02
 * @Version 1.0
 */
@Slf4j
public class RedisMQConsumerContainer {
    public static boolean RUNNING;
    @Autowired
    private RedisTemplate redisTemplate;

    private Map<String,QueueConfiguration> consumerMap= new HashMap<>();

    private ExecutorService executor;

    public RedisMQConsumerContainer() {
        init();
    }

    public void addConsumer(QueueConfiguration configuration) {

        if (consumerMap.containsKey(configuration.getQueue())) {
            log.warn("Key:{} this key already exists, and it will be replaced", configuration.getQueue());
        }
        if (configuration.getConsumer() == null) {
            log.warn("Key:{} consumer cannot be null, this configuration will be skipped", configuration.getQueue());
        }
        consumerMap.put(configuration.getQueue(), configuration);
        executor.submit(new QueueListener(redisTemplate,configuration.getQueue(),configuration.getConsumer()));
        log.info("队列 {} 提交消息任务",configuration.getQueue());

    }

    public void destroy() {
        log.info("Redis消息队列线程池关闭中");
        RUNNING = false;
        this.executor.shutdown();
        log.info("QueueListener exiting.");
        while (!this.executor.isTerminated()) {

        }
        log.info("QueueListener exited.");
    }

    public void init() {
        log.info("消息队列线程池初始化");
        RUNNING = true;
        this.executor = Executors.newCachedThreadPool(r -> {
            final AtomicInteger threadNumber = new AtomicInteger(1);
            return new Thread(r, "RedisMQListener-" + threadNumber.getAndIncrement());
        });
    }
}
