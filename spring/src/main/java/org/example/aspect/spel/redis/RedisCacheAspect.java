package org.example.aspect.spel.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author hao.zhou02
 * @date 2020/2/24
 */
@Component
@Slf4j
@Aspect
public class RedisCacheAspect {

    @Pointcut("@annotation(org.example.aspect.spel.redis.RedisCache)")
    public void pointCut() {
    }

    @Around(value = "pointCut() && @annotation(redisCache)")
    public Object around(ProceedingJoinPoint joinPoint, RedisCache redisCache) throws Throwable {
        Object result = null;
        String cacheKey = null;
        String methodName = null;
        try {
            Method method = getMethod(joinPoint);
            methodName = method.getName();
            String key = redisCache.key();
            log.info("方法{},使用redis缓存key值,解析前:{}", methodName, key);
            cacheKey = parseExpression(key, method, joinPoint.getArgs());
            log.info("方法{},使用redis缓存key值,解析后：{}", methodName, cacheKey);
        } catch (Exception e) {
            log.error("方法{}未使用缓存,解析redis缓存key值异常：{}", methodName, e.getMessage());
        }
        if (StringUtils.isNotBlank(cacheKey)) {
            // get from redis
            result = getFromRedis(cacheKey);
        }
        if (result != null) {
            return result;
        }
        result = joinPoint.proceed();
        if (result != null && StringUtils.isNotBlank(cacheKey)) {
            setForRedis(cacheKey, result);
        }
        return result;
    }

    /**
     * 解析SpEL，获取key值
     *
     * @param expressionLanguage SpEl表达式
     * @param method             method
     * @param args               args
     * @return java.lang.String
     * @date 2020/2/25 17:48
     */
    private String parseExpression(String expressionLanguage, Method method, Object[] args) {
        if (StringUtils.isBlank(expressionLanguage)) {
            throw new RuntimeException("redis key 存在空值，拒绝生成cache key");
        }
        EvaluationContext context = new StandardEvaluationContext();
        ExpressionParser parser = new SpelExpressionParser();
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        if (parameterNames == null) {
            return expressionLanguage;
        }
        for (int i = 0, len = args.length; i < len; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        Expression expression = parser.parseExpression(expressionLanguage);
        return expression.getValue(context, String.class);
    }

    /**
     * 获取method
     *
     * @param joinPoint joinPoint
     * @return java.lang.reflect.Method
     * @date 2020/2/25 17:50
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass()
                                  .getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return method;
    }


    /**
     * getFromRedis
     *
     * @param key key
     * @return java.lang.Object
     * @date 2020/3/6 23:48
     */
    private Object getFromRedis(String key) {
        return null;
    }

    /**
     * setForRedis
     *
     * @param key key
     * @param value value
     * @date 2020/3/6 23:48
     */
    private void setForRedis(String key, Object value) {
        // set redis
    }
}
