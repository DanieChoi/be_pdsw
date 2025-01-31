/*------------------------------------------------------------------------------
 * NAME : NotificationController.java
 * DESC : SSE 방식의 실시간 알림
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/01/21  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.nexus.pdsw.entity.Notification;
import com.nexus.pdsw.service.RedisPublisherService;
import com.nexus.pdsw.service.SseEmitterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sse")
@RequiredArgsConstructor
public class SseEmitterController {
  
  private final SseEmitterService sseEmitterService;
  private final RedisPublisherService redisPublisherService;
  // @Qualifier("1")
  // private final RedisTemplate<String, Object> redisTemplate1;
  
  /*
   *  연결
   *  
   *  @param String counselorId  상담사 ID
   *  @return ResponseEntity<? super GetCounselorListResponseDto>
   */
  @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<SseEmitter> connect(
    @RequestParam(required = true) String counselorId
  ) {

    // 1. SSE 연결하기
    SseEmitter emitter = new SseEmitter(60*1000L);  //만료시간 30초로 설정
    sseEmitterService.createEmitter(counselorId, emitter);

    try {
      // 최초 연결 시 메시지를 안 보내면 503 Service unavailable 에러 발생
      emitter.send(SseEmitter
        .event()
        .id(counselorId)
        .name("connect")
        .data("connected!")
      );
      // emitter.send(SseEmitter.event().name("connect").data("connected!"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // 2. 캐싱 처리된 못 받은 알람 connection 완료 시 밀어주기.
    // if (Boolean.TRUE.equals(redisTemplate1.hasKey(counselorId))) {
    //   ListOperations<String, Object> valuOperations = redisTemplate1.opsForList();
    //   Long size = valuOperations.size(counselorId);
    //   List<Notification> notificationList = valuOperations.range(counselorId, 0, size-1)
    //     .stream()
    //     .map(a->(Notification)a)
    //     .collect(Collectors.toList());
    //   for(Notification notification : notificationList) {
    //     sendLastInfoToCounselor(counselorId, notification);
    //   }
    //   redisTemplate1.delete(counselorId);
    // }

    return ResponseEntity.ok(emitter);
  }

  public void sendToCounselor(String counselorId, String type,String uploadDate,String memberName) {
    ChannelTopic channel = new ChannelTopic(counselorId);
    String message = type + "_" + uploadDate + "_" + memberName + "_" + counselorId + "_sendToCounselor";
    redisPublisherService.publish(channel, message);
  }

  public void sendLastInfoToCounselor(String counselorId,Notification notification) {
    try {
      sseEmitterService.getEmitter(counselorId).send(SseEmitter.event().name("sendToCounselor").data(notification));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}