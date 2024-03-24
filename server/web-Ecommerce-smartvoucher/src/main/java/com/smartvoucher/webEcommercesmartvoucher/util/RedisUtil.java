package com.smartvoucher.webEcommercesmartvoucher.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, String> redisTemplate;
    private final Gson gson;

    public  void clear(){
        RedisConnection redisConnection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        DefaultStringRedisConnection defaultStringRedisConnection = new DefaultStringRedisConnection(redisConnection, redisSerializer);
        defaultStringRedisConnection.flushAll();
    }

    public  String getKeyFrom(Long id, String keyName ,int page, int limit){
        return String.format("%s:%d:%d", keyName, page, limit);
    }

    public ResponseOutput getAllRedis(Long id, String keyName, int page, int  limit){
        String key = getKeyFrom(id, keyName, page, limit);
        String json =this.redisTemplate.opsForValue().get(key);
        return json != null ? gson.fromJson(json, new TypeToken<ResponseOutput>(){}.getType()) : null;
    }

    public void saveToRedis(Long id, String keyName, int page, int limit, ResponseOutput responseOutput) {
        String key = getKeyFrom(id, keyName, page, limit);
        String json = gson.toJson(responseOutput);
        this.redisTemplate.opsForValue().set(key, json);
    }
}
