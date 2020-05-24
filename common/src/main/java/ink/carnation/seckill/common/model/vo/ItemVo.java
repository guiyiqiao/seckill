package ink.carnation.seckill.common.model.vo;

import ink.carnation.seckill.common.model.entity.Item;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author 桂乙侨
 * @Date 2020/5/20 20:32
 * @Version 1.0
 */
@Data

public class ItemVo {
    private Integer itemId;

    private Integer goodsId;

    private BigDecimal itemPrice;

    private Integer itemStock;

    private Date startDate;

    private Date endDate;

    private Byte status;

    private String goodsName;

    private String goodsImg;

    private String goodsDetail;

    private BigDecimal GoodsPrice;

    private Boolean isValid;

    private Integer version;

    public void setIsValid(){
        Date now = new Date();
        if(this.startDate.before(now) && this.endDate.after(now)){
            this.isValid = true;
        }else
            this.isValid = false;
    }

    public Item toItem(){
        return new Item(itemId,goodsId,itemPrice,itemStock,startDate,endDate,version,status);
    }
    public static void setIsValid(List<ItemVo> itemVos){
        itemVos.forEach(x->x.setIsValid());
    }
}
