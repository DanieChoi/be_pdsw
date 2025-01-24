package com.nexus.pdsw.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.pdsw.entity.Notification;
import com.nexus.pdsw.repository.EmitterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisSubscriberService implements MessageListener {

  private final ObjectMapper objectMapper;
  @Qualifier("2")
  private final RedisTemplate<String, String> redisTemplate2;
  private final EmitterRepository emitterRepository;

  @Override
  public void onMessage(Message message, @Nullable byte[] pattern) {
    try {

      String[] strings = message.toString().split("_");

      String type = strings[0];
      String uploadDate = strings[1];
      String name = strings[2];
      // String counselorId = strings[3];
      String counselorId = "NEX21001";
      String category = strings[4];
      SseEmitter emitter = emitterRepository.get(counselorId);

      Notification notification = new Notification(counselorId, type, uploadDate, name);

      if(category.equals("sendToCounselor")){
        emitter.send(SseEmitter.event().name("sendToCounselor").data(notification));
      }else{
        emitter.send(SseEmitter.event().name("sendToCounselor").data(notification));
      }      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
