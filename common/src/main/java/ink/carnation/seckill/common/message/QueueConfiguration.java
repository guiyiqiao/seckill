package ink.carnation.seckill.common.message;

import lombok.Getter;

/**
 * @Author 桂乙侨
 * @Date 2020/5/22 20:05
 * @Version 1.0
 */
@Getter
public class QueueConfiguration {

    private String queue;

    private Consumer consumer;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private QueueConfiguration configuration = new QueueConfiguration();

        public Builder queue(String queue) {
            configuration.queue = queue;
            return this;
        }

        public Builder consumer(Consumer consumer) {
            configuration.consumer = consumer;
            return this;
        }

        public QueueConfiguration build() {
            if (configuration.queue == null || configuration.queue.length() == 0) {
                if (configuration.consumer != null) {
                    configuration.queue = configuration.getClass().getSimpleName();
                }
            }
            return configuration;
        }
    }
}
