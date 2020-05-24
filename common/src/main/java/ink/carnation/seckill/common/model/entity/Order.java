package ink.carnation.seckill.common.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author 桂乙侨
 * @Date 2020/4/19 11:10
 * @Version 1.0
 */
@Data
@Table(name = "order")
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    private Long id;

    private Integer userId;

    private Integer goodsId;

    private String orderAddress;

    private Integer goodsCount;

    private String goodsName;

    private BigDecimal price;

    private Byte orderStatus;

    private Date createDate;

    private Date payDate;
}