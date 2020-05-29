package ink.carnation.seckill.user.model.entity;

import lombok.Data;
import org.springframework.stereotype.Indexed;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @Author 桂乙侨
 * @Date 2020/4/19 11:14
 * @Version 1.0
 */

@Data
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String userName;

    private String password;

    private String email;

    private String telephone;

    private String salt;

    private Set<Role> roles;

}