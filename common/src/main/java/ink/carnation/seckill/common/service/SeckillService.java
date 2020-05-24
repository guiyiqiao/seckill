package ink.carnation.seckill.common.service;

import ink.carnation.seckill.common.model.vo.ItemVo;

import java.util.List;

/**
 * @Author 桂乙侨
 * @Date 2020/5/20 19:32
 * @Version 1.0
 */
public interface SeckillService {

    List<ItemVo> listItemVo();

    long execute(Integer itemId,Integer userId);

    boolean executeOptimistic(Integer itemId);
}
