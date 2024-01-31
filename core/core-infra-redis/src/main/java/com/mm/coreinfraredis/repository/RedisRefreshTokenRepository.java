package com.mm.coreinfraredis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository {
    private final RedisTemplate redisTemplate;
    private static final long REFRESH_TOKEN_EXPIRE_LONG = 259200L;

    public void save(String refreshToken, Long memberId) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken, memberId);
        redisTemplate.expire(refreshToken, REFRESH_TOKEN_EXPIRE_LONG, TimeUnit.SECONDS);
    }

    public void delete(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

    public Optional<Long> findByRefreshToken(String refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long memberId = valueOperations.get(refreshToken);
        return Optional.ofNullable(memberId);
    }
}
