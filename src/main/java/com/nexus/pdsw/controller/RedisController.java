package com.nexus.pdsw.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
  
  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @GetMapping("/redis/{key}")
  public ResponseEntity<?> getRedisKey(@PathVariable String key) {
    HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
    Map<Object, Object> employees = hashOperations.entries(key);
    System.out.println(">>반환 값: " + employees.get(key));

    return new ResponseEntity<>(employees.values(), HttpStatus.OK);
  }
}