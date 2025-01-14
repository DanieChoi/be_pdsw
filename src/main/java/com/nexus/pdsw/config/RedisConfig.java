/*------------------------------------------------------------------------------
 * NAME : RedisConfig.java
 * DESC : Redis 환경설정
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2025 Nexus Communication All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/01/09  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
  
  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  @Value("${spring.data.redis.password}")
  private String redisPassword;

  @Value("${spring.data.redis.database}")
  private int redisDatabaseIdx;

  // Redis 연결을 위한 'Connection' 생성합니다.
  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
      
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

    redisStandaloneConfiguration.setHostName(redisHost);
    redisStandaloneConfiguration.setPort(redisPort);
    redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
    redisStandaloneConfiguration.setDatabase(redisDatabaseIdx);

    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
    return lettuceConnectionFactory;
  }

  //RedisTemplate 사용을 위한 추가
  // redisTemplate: default Value Type = JSON
  // If you want to use String Type, you can change ValueSerializer to StringRedisSerializer or Use StringRedisTemplate
  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());

    // 일반적인 key:value의 경우 시리얼라이저
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());

    // Hash를 사용할 경우 시리얼라이저
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new StringRedisSerializer());

    // 모든 경우
    redisTemplate.setDefaultSerializer(new StringRedisSerializer());

    return redisTemplate;
  }
}