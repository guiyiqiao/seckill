package ink.carnation.seckill.common.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author 桂乙侨
 * @Date 2020/5/7 16:29
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrder implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "")
    private Integer id;

    private Long orderId;

    private Integer userId;

    private Integer itemId;
}
