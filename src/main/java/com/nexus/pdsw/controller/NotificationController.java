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
 * 2025/02/05  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.nexus.pdsw.dto.request.GetSubscribeRequestDto;
import com.nexus.pdsw.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@RestController
public class NotificationController {

  private final NotificationService notificationService;
  
  /*
   *  실시간 이벤트 구독
   *  
   *  @param String counselorId  상담사 ID
   *  @return ResponseEntity<SseEmitter>
   */
  @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<SseEmitter> subscribe(
    @RequestBody GetSubscribeRequestDto requestBody
  ) {
    
    return ResponseEntity.ok(notificationService.subscribe(requestBody));

  }

}
