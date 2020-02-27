package org.example.module;

import org.example.aspect.spel.redis.RedisCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author nurhier
 * @date 2020/2/27
 */
@Service
public class UserServiceImpl implements UserService {
    @RedisCache(key = "#userId", timeout = 10, timeUnit = TimeUnit.MINUTES)
    @Override
    public String getUserName(Long userId) {
        return "mock:" + userId;
    }
}
