/*------------------------------------------------------------------------------
 * NAME : RedisMonitorController.java
 * DESC : Redis 9번방에서 모니터링 정보 가져오기
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/01/31  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.pdsw.dto.response.monitor.GetDialerChannelStatusInfoResponseDto;
import com.nexus.pdsw.dto.response.monitor.GetProcessStatusInfoResponseDto;
import com.nexus.pdsw.service.RedisMonitorService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/monitor")
@RequiredArgsConstructor
public class RedisMonitorController {

  private final RedisMonitorService redisMonitorService;
  
  /*
   *  타 시스템 프로세스 상태정보 가져오기
   *  
   *  @return ResponseEntity<? super GetProcessStatusInfoResponseDto>
   */
  @GetMapping("/process")
  public ResponseEntity<? super GetProcessStatusInfoResponseDto> getProcessStatusInfo() {
    ResponseEntity<? super GetProcessStatusInfoResponseDto> response = redisMonitorService.getProcessStatusInfo();
    return response;
  }
  
  /*
   *  Dialer 채널 상태 정보 가져오기
   *  
   *  @param deviceId         Dialer 장비ID
   *  @return ResponseEntity<? super GetDialerChannelStatusInfoResponseDto>
   */
  @GetMapping("/dialer/{deviceId}/channel")
  public ResponseEntity<? super GetDialerChannelStatusInfoResponseDto> getDialerChannelStatusInfo(
    @PathVariable("deviceId") String deviceId
  ) {
    ResponseEntity<? super GetDialerChannelStatusInfoResponseDto> response = redisMonitorService.getDialerChannelStatusInfo(deviceId);
    return response;
  }
}
