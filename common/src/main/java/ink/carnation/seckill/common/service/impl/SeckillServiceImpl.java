package ink.carnation.seckill.common.service.impl;

import ink.carnation.seckill.common.mapper.SeckillMapper;
import ink.carnation.seckill.common.mapper.tk.ItemMapper;
import ink.carnation.seckill.common.message.Producer;
import ink.carnation.seckill.common.model.entity.Item;
import ink.carnation.seckill.common.model.enums.RedisKey;
import ink.carnation.seckill.common.model.message.OrderMessage;
import ink.carnation.seckill.common.model.vo.ItemVo;
import ink.carnation.seckill.common.service.SeckillService;
import ink.carnation.seckill.common.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author 桂乙侨
 * @Date 2020/5/20 19:39
 * @Version 1.0
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    @Resource
    private SeckillMapper seckillMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private ItemMapper itemMapper;

    @Autowired
    @Qualifier("orderProducer")
    private Producer producer;

    @Override
    public List<ItemVo> listItemVo() {
        List<ItemVo> itemVos= seckillMapper.listItemVo();
        itemVos.forEach(x->x.setIsValid());
        return itemVos;
    }

    @Override
    /**
     * 执行秒杀
     * 减库存
     * 生成订单号
     * 添加订单
     */
    public long execute(Integer itemId,Integer userId) {

        String itemKey = RedisKey.ITEM_STOCK.getValue()+itemId;
        //检查当前用户是否已抢购过
        String userSet = RedisKey.ITEM_USER_SET.getValue()+itemId;
        //进行抢购
        Object obj = redisTemplate.opsForList().rightPop(itemKey);
        if(obj == null)
            return -1;
        if(redisTemplate.opsForSet().isMember(userSet,userId)){
            log.error(userId+"不能重复抢购该商品");
            redisTemplate.opsForList().leftPush(itemKey,obj);
            return -1;
        }
        //抢购成功,处理订单逻辑，即可
        //生成订单号，并发送消息至消息队列，进行异步下单
        long orderId = SnowFlake.nextId();
        OrderMessage message = new OrderMessage(userId,orderId,itemId);
        String queue = RedisKey.ITEM_MESSAGE.getValue()+itemId;
        producer.produce(queue,message);
        redisTemplate.opsForSet().add(userSet,userId);
        log.info("user :"+userId+"参与抢购"+itemId+"号商品");
        return orderId;
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean executeOptimistic(Integer itemId){
        //获取乐观锁
        Item item = seckillMapper.getVersion(itemId);
        if (item == null)
            return false;
        //执行更新操作
        int execute = seckillMapper.execute(itemId,item.getVersion());
        if(execute == 0)
            return false;
        //产生订单号，生成订单
        return true;
}

}

