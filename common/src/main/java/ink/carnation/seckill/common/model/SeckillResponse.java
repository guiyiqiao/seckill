package ink.carnation.seckill.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author 桂乙侨
 * @Date 2020/4/19 11:01
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class SeckillResponse<T> {
    private int code;
    private String message;
    private T data = null;

    public SeckillResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SeckillResponse success() {
        return new SeckillResponse(200, "操作成功");
    }

    public static <T> SeckillResponse<T> success(T data) {
        return new SeckillResponse<T>(200, "操作成功", data);
    }

    public static SeckillResponse failure() {
        return new SeckillResponse(500, "操作失败");
    }

    public static SeckillResponse failure(String message) {
        return new SeckillResponse(500, message);
    }
}
