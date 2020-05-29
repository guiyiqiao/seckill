package ink.carnation.seckill.user.service.impl;

import ink.carnation.seckill.user.mapper.tk.UserMapper;
import ink.carnation.seckill.user.model.entity.User;
import ink.carnation.seckill.user.service.UserService;
import ink.carnation.seckill.user.util.TokenUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @Author 桂乙侨
 * @Date 2020/5/26 19:06
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public String login(String userName, String password) {
        return TokenUtil.sign(userName,System.currentTimeMillis());

    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByUserName(String userName) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName",userName);
        List<User> users = userMapper.selectByExample(example);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User validUser(String userName, String password) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName",userName);
        criteria.andEqualTo("password",password);
        List<User> users = userMapper.selectByExample(example);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> listUser() {
        return userMapper.selectAll();
    }
}
