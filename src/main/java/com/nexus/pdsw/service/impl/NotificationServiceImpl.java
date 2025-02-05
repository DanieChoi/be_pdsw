/*------------------------------------------------------------------------------
 * NAME : NotificationServiceImpl.java
 * DESC : 알림 이벤트 기능 구현체
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/02/04  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.nexus.pdsw.dto.request.GetSubscribeRequestDto;
import com.nexus.pdsw.service.NotificationService;
import com.nexus.pdsw.service.RedisMessageService;
import com.nexus.pdsw.service.SseEmitterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

  private final SseEmitterService sseEmitterService;
  private final RedisMessageService redisMessageService;

  /*
   *  알림 이벤트 구독
   *  
   *  @param GetSubscribeRequestDto requestBody   로그인 상담사 정보
   *  @return SseEmitter
   */
  @Override
  public SseEmitter subscribe(GetSubscribeRequestDto requestBody) {
    SseEmitter sseEmitter = sseEmitterService.createEmitter(requestBody.getCounselorId());
    sseEmitterService.send("MsgF", requestBody.getCounselorId(), sseEmitter);

    redisMessageService.subscribe(requestBody.getTenantId());

    sseEmitter.onTimeout(sseEmitter::complete);
    sseEmitter.onError((e) -> sseEmitter.complete());
    sseEmitter.onCompletion(() -> {
      sseEmitterService.deleteEmitter(requestBody.getCounselorId());
      redisMessageService.removeSubscribe(requestBody.getTenantId());
    });
    return sseEmitter;
  }

  // @Override
  // @Transactional
  // public void sendNotification(NotificationEvent event) {
  //   String key = "";
  //   redisMessageService.publish(key, NotificationDto);
  // }
  
}
