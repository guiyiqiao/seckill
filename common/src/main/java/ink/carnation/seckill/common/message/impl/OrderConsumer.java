package ink.carnation.seckill.common.message.impl;

import ink.carnation.seckill.common.exception.SQLSystemException;
import ink.carnation.seckill.common.mapper.tk.ItemOrderMapper;
import ink.carnation.seckill.common.message.Consumer;
import ink.carnation.seckill.common.model.entity.ItemOrder;
import ink.carnation.seckill.common.model.message.OrderMessage;
import ink.carnation.seckill.common.service.SeckillService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author 桂乙侨
 * @Date 2020/5/21 16:59
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
public class OrderConsumer implements Consumer {

    private ItemOrderMapper itemOrderMapper;

    private SeckillService seckillService;

    public OrderConsumer(){
    }

    @Override
    public void consume(Object message) {
        if(message instanceof OrderMessage){
            OrderMessage msg =(OrderMessage) message;
            log.info("开始消费消息{}",msg.toString());
            int count = 5;
            boolean task = false;
            while(!(task = seckillService.executeOptimistic(msg.getItemId())) && count-- > 0)
                log.info("{}号订单，乐观锁添加订单，请求第{}次",msg.getOrderId(),(5-count));
            if(!task){
                //修改库存失败，丢弃消息
                log.error("{}号订单消息被丢弃",msg.getOrderId());
                return;
            }
            ItemOrder itemOrder = new ItemOrder(null,msg.getOrderId(),msg.getUserId(),msg.getItemId());
            int flag = itemOrderMapper.insert(itemOrder);
            if(flag <= 0)
                throw new SQLSystemException();
            log.info(msg.getOrderId()+"号订单处理成功");
        }
    }
}
