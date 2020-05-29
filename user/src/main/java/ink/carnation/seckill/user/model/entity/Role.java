package ink.carnation.seckill.user.model.entity;

import lombok.Data;

import java.util.Set;

/**
 * @Author 桂乙侨
 * @Date 2020/5/26 19:01
 * @Version 1.0
 */
@Data

public class Role {
    private Integer id;
    private String role;

    private Set<Permission> permissions;
}
