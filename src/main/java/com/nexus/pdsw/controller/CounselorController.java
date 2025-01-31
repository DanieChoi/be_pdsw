/*------------------------------------------------------------------------------
 * NAME : CounselorController.java
 * DESC : 상담사 내역 불러오기
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/01/15  최상원                       초기작성
 * 2025/01/18  최상원                       상담사 상태정보 가져오기 추가
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.pdsw.dto.response.counselor.GetCounselorListResponseDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorStatusListResponseDto;
import com.nexus.pdsw.dto.response.counselor.PutSubscribeResponseDto;
import com.nexus.pdsw.service.CounselorService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/counselor")
@RequiredArgsConstructor
public class CounselorController {
  
  private final CounselorService counselorService;

  /*
   *  상담사 로그인 시 채널 구독하기
   *  
   *  @param String tenantId  상담사 소속 테넌트ID
   *  @param String roleId    상담사 역할 ID(1: 상담사, 2: 파트매니저, 3: 그룹매니저, 4: 테넌트메니저, 5: 시스템 메니저, 6: 전체)
   *  @return ResponseEntity<? super PutSubscribeResponseDto>
   */
  @PutMapping("/subscribe")
  public ResponseEntity<? super PutSubscribeResponseDto> subscribe(
    @RequestParam(required = true) String tenantId,
    @RequestParam(required = true) String roleId
  ) {
      
    ResponseEntity<? super PutSubscribeResponseDto> response = counselorService.subscribe(tenantId, roleId);
    return response;
  }
  

  /*
   *  상담사 리스트 가져오기
   *  
   *  @param String tenantId  상담사 소속 테넌트ID
   *  @param String roleId    상담사 역할 ID(1: 상담사, 2: 파트매니저, 3: 그룹매니저, 4: 테넌트메니저, 5: 시스템 메니저, 6: 전체)
   *  @return ResponseEntity<? super GetCounselorListResponseDto>
   */
  @GetMapping("/list")
  public ResponseEntity<? super GetCounselorListResponseDto> getCounselorList(
    @RequestParam(required = true) String tenantId,
    @RequestParam(required = true) String roleId
  ) {
    ResponseEntity<? super GetCounselorListResponseDto> response = counselorService.getCounselorList(tenantId, roleId);
    return response;
  }

  /*
   *  상담사 상태정보 가져오기
   *  
   *  @param String tenantId        테넌트ID
   *  @param String counselorIds    상당원ID's
   *  @return ResponseEntity<? super GetCounselorStatusListResponseDto>
   */
  @GetMapping("/state")
  public ResponseEntity<? super GetCounselorStatusListResponseDto> getCounselorStatusList(
    @RequestParam(required = true) String tenantId,
    @RequestParam(required = true) String counselorIds
  ) {
    ResponseEntity<? super GetCounselorStatusListResponseDto> response = counselorService.getCounselorStatusList(tenantId, counselorIds);
    return response;
  }
}
