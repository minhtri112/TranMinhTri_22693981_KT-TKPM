package iuh.fit.jwt_demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public  class TokenBlacklistService {
    private final String PREFIX = "blacklist:";


    private StringRedisTemplate stringRedisTemplate;

    public TokenBlacklistService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void backList(String jti,long ttlSeconds){
        stringRedisTemplate.opsForValue()
                .set(PREFIX + jti,"1", Duration.ofSeconds(ttlSeconds));
    }
    public boolean isBackList(String jti){
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(PREFIX + jti));
    }

}
