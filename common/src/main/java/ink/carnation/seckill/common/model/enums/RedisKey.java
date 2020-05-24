package ink.carnation.seckill.common.model.enums;

/**
 * @Author 桂乙侨
 * @Date 2020/4/25 17:00
 * @Version 1.0
 */
public enum RedisKey {
    ITEM_MESSAGE("seckill::message::item::"),
    ITEM_STOCK("seckill::item::stock::"),
    ITEM_USER_SET("seckill::item::users::set::");


    private String value;

    RedisKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
