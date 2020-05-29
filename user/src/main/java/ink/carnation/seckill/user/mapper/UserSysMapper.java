package ink.carnation.seckill.user.mapper;

import ink.carnation.seckill.user.model.entity.User;

/**
 * @Author 桂乙侨
 * @Date 2020/5/26 19:12
 * @Version 1.0
 */
@Deprecated
public interface UserSysMapper {

    User queryUserById(Integer id);
}
