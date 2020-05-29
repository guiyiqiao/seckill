package ink.carnation.seckill.user.service;

import ink.carnation.seckill.user.model.entity.User;

import java.util.List;

/**
 * @Author 桂乙侨
 * @Date 2020/5/26 19:05
 * @Version 1.0
 */
public interface UserService {

    String login(String userName,String password);

    User getUserById(Integer id);

    User getUserByUserName(String userName);

    User validUser(String userName,String password);

    List<User> listUser();
}
