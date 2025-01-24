package com.nexus.pdsw.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisPublisherService {
  @Qualifier("2")
  private final RedisTemplate<String, String> redisTemplate2;


  public void publish(ChannelTopic topic, String message) {
    redisTemplate2.convertAndSend(topic.getTopic(), message);
  }
}
