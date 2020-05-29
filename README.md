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

## API
商品列表展示接口 
GET /seckill/items 
查询结果集为
```java
//映射结果集类
public class ItemVo {
    //秒杀商品id
    private Integer itemId;
    //所属商品id
    private Integer goodsId;
    //double 的秒杀商品价格（秒杀价格）
    private BigDecimal itemPrice;
    //秒杀商品库存
    private Integer itemStock;
    //秒杀开始时间
    private Date startDate;
    //秒杀结束时间
    private Date endDate;
    //秒杀状态 0 未开始  1 进行中 2 已结束（这个还没做）
    private Byte status;
    //商品名
    private String goodsName;
    //商品图片地址，目前存的时字符串，还没搞图片
    private String goodsImg;
    //商品细节
    private String goodsDetail;
    //类似double 的商品价格（原价）
    private BigDecimal GoodsPrice;
    //是否有效，true有效 false 无效
    private Boolean isValid;
    //后端乐观锁的版本号，你可以忽略他
    private Integer version;
}
```

## 用户权限设计
使用apache shiro 框架，JWT(JOSN WEB TOKEN)处理无状态登陆
，使用Redis缓存token以做到过期时间后失效  
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
角色信息
```java
public class Role {
    private Integer id;
    private String role;

    private Set<Permission> permissions;
}
```
权限信息
```java
public class Permission {
    private Integer id;
    private String permission;
}
```
鉴权的流程为下。  
1.用户登陆之后（user/login），使用密码对账号进行签名生成并返回token并设置过期时间；成功返回加密的AccessToken放在Response Header的Authorization属性中，失败直接返回401错误(帐号或密码不正确)。

2.前端将token保存到本地，并且每次发送请求时都在header上携带token。

3.shiro过滤器拦截到请求并获取header中的token，并提交到自定义realm的doGetAuthenticationInfo方法。

4.通过jwt解码获取token中的用户名，从数据库中查询到密码之后根据密码生成jwt效验器并对token进行验证。
