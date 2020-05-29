package ink.carnation.seckill.user.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ink.carnation.seckill.user.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;

import java.util.Date;

/**
 * @Author 桂乙侨
 * @Date 2020/5/27 9:46
 * @Version 1.0
 */
@Slf4j
public class TokenUtil {
    //这里的token属性配置最好写在配置文件中，这里为了方面直接写成静态属性
    public static final long EXPIRE_TIME= 5*60*1000;//token到期时间5分钟，毫秒为单位
    public static final long REFRESH_EXPIRE_TIME=30*60;//RefreshToken到期时间为30分钟，秒为单位
    private static final String TOKEN_SECRET="ljdyaishijin**3nkjnj??";  //密钥盐

    public static String sign(String userName,Long currentTime){
        try {

            Date expireAt=new Date(currentTime+EXPIRE_TIME);
            String token = JWT.create()
                    .withIssuer("auth0")//发行人
                    .withClaim("userName",userName)//存放数据
                    .withClaim("currentTime",currentTime)
                    .withExpiresAt(expireAt)//过期时间
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
            return token;
        } catch (IllegalArgumentException| JWTCreationException je) {
            throw new  UnauthenticatedException("生成token失败!");
        }
    }


    public static Boolean verify(String token){
        try {
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();//创建token验证器
            DecodedJWT decodedJWT=jwtVerifier.verify(token);
            log.info("认证通过：");
            log.info("userName: " + decodedJWT.getClaim("userName").asString());
            log.info("过期时间：      " + decodedJWT.getExpiresAt());
            return true;
        }catch (Exception e){
            return false;
        }


    }



    public static String getUserName(String token){
        try{
            DecodedJWT decodedJWT=JWT.decode(token);
            return decodedJWT.getClaim("userName").asString();

        }catch (JWTCreationException e){
            return null;
        }
    }
    public static Long getCurrentTime(String token){
        try{
            DecodedJWT decodedJWT=JWT.decode(token);
            return decodedJWT.getClaim("currentTime").asLong();

        }catch (JWTCreationException e){
            return null;
        }
    }

}
