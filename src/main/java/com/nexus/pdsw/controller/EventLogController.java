/*------------------------------------------------------------------------------
 * NAME : EventLogController.java
 * DESC : 이벤트 로그 기록
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

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.pdsw.dto.request.PostEventLogRequestDto;
import com.nexus.pdsw.dto.response.eventLog.PostEventLogResponseDto;
import com.nexus.pdsw.service.EventLogService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1/log")
@RequiredArgsConstructor
@RestController
public class EventLogController {

  private final EventLogService eventLogService;
  
  /*  
   *  이벤트 로그 저장하기
   *  
   *  @param PostEventLogRequestDto requestBody  이벤트 로그 전달 DTO
   *  @return ResponseEntity<? super PostEventLogResponseDto>
   */
  @PostMapping("/save")
  public ResponseEntity<? super PostEventLogResponseDto> saveEventLog(
    @RequestBody PostEventLogRequestDto requestBody
  ) {
    ResponseEntity<? super PostEventLogResponseDto> response = eventLogService.saveEventLog(requestBody);
    return response;
  }
}
