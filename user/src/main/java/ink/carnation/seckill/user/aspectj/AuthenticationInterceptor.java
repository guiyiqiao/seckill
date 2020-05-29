package ink.carnation.seckill.user.aspectj;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import ink.carnation.seckill.user.annotation.LoginToken;
import ink.carnation.seckill.user.annotation.PassToken;
import ink.carnation.seckill.user.model.entity.User;
import ink.carnation.seckill.user.service.UserService;
import ink.carnation.seckill.user.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author 桂乙侨
 * @Date 2020/4/2 10:13
 * @Version 1.0
 */
//@Component
    @Deprecated
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("HandlerInterceptor 开始拦截");
        String token = request.getHeader("token");
        log.info("token =" + token);
        //不是映射到方法 直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注解，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            log.info("passtoken");
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                // 执行认证
                if (token == null) {
                    log.info("无token");
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user name
                String userName;
                try {
                    userName = TokenUtil.getUserName(token);
                    log.info(userName);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                log.info(userName + "请求登陆");
                User user = userService.getUserByUserName(userName);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
                return TokenUtil.verify(token);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("HandlerInterceptor 拦截完成");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
