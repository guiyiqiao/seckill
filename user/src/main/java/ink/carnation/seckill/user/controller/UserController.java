package ink.carnation.seckill.user.controller;

import ink.carnation.seckill.user.model.CacheFix;
import ink.carnation.seckill.user.model.SimpleResult;
import ink.carnation.seckill.user.model.entity.User;
import ink.carnation.seckill.user.service.UserService;
import ink.carnation.seckill.user.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author 桂乙侨
 * @Date 2020/5/26 9:51
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("login")
    public SimpleResult<String> login(String userName,
                                 String password,
                                 HttpServletResponse response
                                 ){
        log.info("开始登陆验证");
        if(Objects.isNull(userName) || Objects.isNull(password))
            return new SimpleResult<>(401,"用户名id或密码不能为空");
        User user = userService.validUser(userName,password);
        if(user == null)
            return new SimpleResult<>(401,"用户名或密码错误");
        String token = userService.login(userName,password);
        redisTemplate.boundValueOps(CacheFix.REFRESH_FIX.getValue()+userName).set(TokenUtil.getUserName(token),5, TimeUnit.MINUTES);
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return new SimpleResult<>(200,"ok",token);
    }

    @GetMapping
    @RequiresAuthentication
    public SimpleResult<List<User>> listUser(){
        return new SimpleResult<>(200,"ok" ,
                userService.listUser());
    }
}
