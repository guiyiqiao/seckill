package ink.carnation.seckill.common.message;

/**
 * @Author 桂乙侨
 * @Date 2020/5/21 17:00
 * @Version 1.0
 */
public interface Producer {

    void produce(String queue,Object message);
}
