package ink.carnation.seckill.user.model;

/**
 * @Author 桂乙侨
 * @Date 2020/5/28 9:22
 * @Version 1.0
 */
public enum  CacheFix {
    REFRESH_FIX("refresh::token::");

    private String value;

    private CacheFix(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
