# 秒杀系统设计

## 数据库实体设计
商品信息表设计
```java
public class Goods implements Serializable {
    //主键id自增
    private Integer id;

    private String goodsName;

    private String goodsImg;

    private Integer goodsStock;

    private String goodsDetail;

    private BigDecimal GoodsPrice;
}
```

商品订单表
```java
public class Order implements Serializable {

    @Id
    //主键字符串类型
    private String id;

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
```
秒杀商品信息表
```java
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
```
秒杀商品订单表
```java
public class ItemOrder implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String orderId;

    private Integer userId;

    private Integer goodsId;
}
```
用户信息表
```java
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String nickName;

    private String password;

    private String email;

    private String telephone;

    private String salt;
}
```



## 界面层

展示所有秒杀商品 ok  
点击进去详情页,判断是否可秒杀
点击秒杀

## 超卖问题 解决方案

1.**使用悲观锁处理**   
select * from item where id = #{id} for update  
update set ;  
利用InnoDB的行级锁提高性能,易产生死锁
https://www.cnblogs.com/zery/p/11801168.html  
2.**使用乐观锁**

添加版本号,本项目采用  
3.**使用队列**  
将多线程请求转变到一个队列，转为单线程

## 使用缓存减轻数据库压力
使用单线程Redis 的队列，作为秒杀商品的库存,利用Schedule定时查看并预热  

处理秒杀商品逻辑，实现一人只能抢一次

## 使用MQ削峰填谷，异步消费
本项目使用了Redis作消息队列，用于处理具体订单的生成

隐藏秒杀地址、限流