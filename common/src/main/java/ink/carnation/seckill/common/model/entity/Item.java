package ink.carnation.seckill.common.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author 桂乙侨
 * @Date 2020/4/19 11:03
 * @Version 1.0
 */
@Data
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor

public class Item implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private Integer goodsId;
    private BigDecimal itemPrice;
    private Integer itemStock;
    private Date startDate;
    private Date endDate;
    private Integer version;
    private Byte status;
}
