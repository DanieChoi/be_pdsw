package com.nexus.pdsw.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
  
  private final RedisTemplate<String, Object> redisTemplate;

  public RedisService(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void setData(String key, String value,Long expiredTime){
    redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
  }

  public String getData(String key){
    return (String) redisTemplate.opsForValue().get(key);
  }

  public void deleteData(String key){
    redisTemplate.delete(key);
  }  
}
