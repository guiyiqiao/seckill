package ink.carnation.seckill.user.config.shiro;

import ink.carnation.seckill.user.model.JWTToken;
import ink.carnation.seckill.user.model.entity.Permission;
import ink.carnation.seckill.user.model.entity.Role;
import ink.carnation.seckill.user.model.entity.User;
import ink.carnation.seckill.user.service.UserService;
import ink.carnation.seckill.user.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.Token;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author 桂乙侨
 * @Date 2020/5/26 10:00
 * @Version 1.0
 */
@Slf4j
@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * shiro 的获取权限接口,权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("鉴权");
        //获取请求登陆的用户名
        String userName = TokenUtil.getUserName(principalCollection.toString());

        User user = userService.getUserByUserName(userName);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //封装角色与权限到 authorizationInfo
        for(Role role : user.getRoles()){
            authorizationInfo.addRole(role.getRole());
            for(Permission per : role.getPermissions()){
                authorizationInfo.addStringPermission(per.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * shiro的认证鉴权环节，登陆认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("验证");
        String token = (String) authenticationToken.getCredentials();
        log.info("credentials = "+ token);
        String userName = TokenUtil.getUserName(token);
        User user = userService.getUserByUserName(userName);
        boolean flag = TokenUtil.verify(token);
        if(flag &&user != null ){
            return new SimpleAuthenticationInfo(
                    token,token,getName()
            );
        }
        else
            throw new AuthenticationException();

    }
}
