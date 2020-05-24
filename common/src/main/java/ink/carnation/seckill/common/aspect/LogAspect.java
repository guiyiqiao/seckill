package ink.carnation.seckill.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 桂乙侨
 * @Date 2020/4/25 16:54
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    private HttpServletRequest request = null;

    //private Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * controller包下所有请求都将拦截
     *
     * @param point
     * @return
     */
    @Around("execution(* ink.carnation.seckill.common.controller.*.*(..))")
    public Object checkController(ProceedingJoinPoint point) {
        StringBuffer stringBuffer = new StringBuffer();
        long start = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        String ip = getIpAddress(request);
        Object object = null;
        Object[] args = point.getArgs();
        stringBuffer.append("请求地址：" + url + "  请求方法 " + method + " 请求 IP：" + ip);
        stringBuffer.append("方法参数：[");
        for (Object o : point.getArgs()) {
            stringBuffer.append("  " + o.toString());
        }
        stringBuffer.append("]");
        try {
            object = point.proceed();
        } catch (Throwable throwable) {
            stringBuffer.append("   方法执行出错！");
        } finally {
            long end = System.currentTimeMillis();
            stringBuffer.append(" 执行结束，共执行：" + (end - start));
            log.info(stringBuffer.toString());
        }
        return object;

    }

    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
