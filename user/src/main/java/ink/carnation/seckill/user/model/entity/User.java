package ink.carnation.seckill.user.model.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author 桂乙侨
 * @Date 2020/4/19 11:14
 * @Version 1.0
 */

@Data
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