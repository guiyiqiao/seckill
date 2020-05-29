package ink.carnation.seckill.user.model;

import lombok.Data;

import java.util.Objects;

/**
 * @Author 桂乙侨
 * @Date 2020/5/27 11:03
 * @Version 1.0
 */
@Data
public class SimpleResult<T> {
    private int code;

    private String message;

    private T data;

    public SimpleResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public SimpleResult(int code, String message) {
        this.code = code;
        this.message = message;

    }
    public SimpleResult() {

    }

    public static SimpleResult success(int code , String msg){
        return new SimpleResult(200,"ok");
    }

    public static SimpleResult failure(int code,String msg){
        return new SimpleResult(401,"未通过身份验证");
    }
}
