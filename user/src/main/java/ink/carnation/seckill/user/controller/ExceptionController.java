package ink.carnation.seckill.user.controller;

import ink.carnation.seckill.user.model.SimpleResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 桂乙侨
 * @Date 2020/5/27 16:56
 * @Version 1.0
 */
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public SimpleResult handle401(ShiroException e) {
        return SimpleResult.failure(401,"未授权");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public SimpleResult unauthenticate( ) {
        return  SimpleResult.failure(401,"未授权");
    }


}
