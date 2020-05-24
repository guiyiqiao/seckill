package ink.carnation.seckill.common.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author 桂乙侨
 * @Date 2020/4/26 13:39
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    Integer userId;
    Long orderId;
    Integer itemId;
}
