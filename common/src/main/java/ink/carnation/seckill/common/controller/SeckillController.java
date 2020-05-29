package ink.carnation.seckill.common.controller;

import ink.carnation.seckill.common.model.SeckillResponse;
import ink.carnation.seckill.common.model.vo.ItemVo;
import ink.carnation.seckill.common.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 桂乙侨
 * @Date 2020/5/19 19:16
 * @Version 1.0
 */
@RestController
@RequestMapping("seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    /**
     * 查询所有秒杀商品，不分页
     * @return
     */
    @GetMapping("items")
    public SeckillResponse<List<ItemVo>> listItemVo(){
        List<ItemVo> itemVos = seckillService.listItemVo();
        return SeckillResponse.success(itemVos);
    }

    /**
     * 执行秒杀,传入参数
     * @Param userId 用户id
     * @param itemId 待秒杀商品id
     * @return
     */
    @PostMapping("execute/{itemId}")
    public SeckillResponse executeSeckill(@PathVariable("itemId") Integer itemId,Integer userId){
        seckillService.execute(itemId,userId);
        return SeckillResponse.success();
    }


}
