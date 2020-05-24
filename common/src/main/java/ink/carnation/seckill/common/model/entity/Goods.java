package ink.carnation.seckill.common.model.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author 桂乙侨
 * @Date 2020/5/7 16:29
 * @Version 1.0
 */
@Data
public class Goods implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String goodsName;

    private String goodsImg;

    private Integer goodsStock;

    private String goodsDetail;

    private BigDecimal GoodsPrice;
}
