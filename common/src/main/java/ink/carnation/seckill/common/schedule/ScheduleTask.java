package ink.carnation.seckill.common.schedule;

import ink.carnation.seckill.common.mapper.SeckillMapper;
import ink.carnation.seckill.common.mapper.tk.ItemMapper;
import ink.carnation.seckill.common.mapper.tk.ItemOrderMapper;
import ink.carnation.seckill.common.message.Consumer;
import ink.carnation.seckill.common.message.QueueConfiguration;
import ink.carnation.seckill.common.message.RedisMQConsumerContainer;
import ink.carnation.seckill.common.message.impl.OrderConsumer;
import ink.carnation.seckill.common.model.entity.Item;
import ink.carnation.seckill.common.model.enums.RedisKey;
import ink.carnation.seckill.common.model.vo.ItemVo;
import ink.carnation.seckill.common.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 桂乙侨
 * @Date 2020/4/19 20:39
 * @Version 1.0
 */
@Component
@Slf4j
public class ScheduleTask {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private ItemOrderMapper itemOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisMQConsumerContainer mqContainer;

    @Scheduled(cron = "0/5 * * * * ?")
    @Transactional
    public void startSeckill() {
        List<Item> itemVos = seckillMapper.listItemForTask();
        String redisKey ;
        for (Item item : itemVos) {
            redisKey = RedisKey.ITEM_STOCK.getValue()+item.getId();
            log.info(item.getId() + "号秒杀商品开始预热！key = "+redisKey);
            int stock = item.getItemStock();
            //将库存预热到Redis缓存中
            redisTemplate.delete(redisKey);
            while (stock-- > 0)
                redisTemplate.opsForList().leftPush(redisKey,item.getId());
            item.setVersion(item.getVersion()+1);
            item.setStatus((byte)1);
            itemMapper.updateByPrimaryKey(item);
            String queue = RedisKey.ITEM_MESSAGE.getValue()+item.getId();
            Consumer orderConsumer = new OrderConsumer(itemOrderMapper,seckillService);
            mqContainer.addConsumer(
                    QueueConfiguration.builder().queue(queue).consumer(orderConsumer).build()
            );
            log.info(item.getId() + "号秒杀商品预热完毕！");
        }
    }

}
