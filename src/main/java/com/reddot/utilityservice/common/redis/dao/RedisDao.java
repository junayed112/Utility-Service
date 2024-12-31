package com.reddot.utilityservice.common.redis.dao;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Data
@Slf4j
@NoArgsConstructor
@Service
public class RedisDao {

    @Autowired
    private RedisTemplate redisTemplate;

    public void setValueWithTimeout(String key, String value, int timeOut) {
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        ops.set(key, value, timeOut, TimeUnit.SECONDS);
    }

    public void setValue(String key, String value) {
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        ops.set(key, value);
    }

    public <E> void setObject(String key, E obj) {
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        String value = new Gson().toJson(obj);
        ops.set(key, value);
    }

    public <T> T getObject(String key, Class<T> obj) {
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        String value = ops.get(key);
        return new Gson().fromJson(value,obj);
    }

    public boolean hasKey(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public String getValue(String key) {
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        return ops.get(key);
    }

    public void deleteValue(String key) {
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        ops.getOperations().delete(key);
    }

    public long increment(String key) {
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        return ops.increment(key, 1);
    }
}

