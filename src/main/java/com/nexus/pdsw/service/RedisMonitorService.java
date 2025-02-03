/*------------------------------------------------------------------------------
 * NAME : RedisMonitorService.java
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
package com.nexus.pdsw.service;

import org.springframework.http.ResponseEntity;

import com.nexus.pdsw.dto.response.monitor.GetDialerChannelStatusInfoResponseDto;
import com.nexus.pdsw.dto.response.monitor.GetProcessStatusInfoResponseDto;

public interface RedisMonitorService {
  
  /*
   *  타 시스템 프로세스 상태정보 가져오기
   *  
   *  @return ResponseEntity<? super GetProcessStatusInfoResponseDto>
   */
  ResponseEntity<? super GetProcessStatusInfoResponseDto> getProcessStatusInfo();

  /*
   *  Dialer 채널 상태 정보 가져오기
   *  
   *  @param String deviceId         Dialer 장비ID
   *  @return ResponseEntity<? super GetDialerChannelStatusInfoResponseDto>
   */
  ResponseEntity<? super GetDialerChannelStatusInfoResponseDto> getDialerChannelStatusInfo(String deviceId);
  
}
