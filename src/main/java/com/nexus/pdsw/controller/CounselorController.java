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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.pdsw.dto.request.PostCounselorListRequestDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorInfoListResponseDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorListResponseDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorStatusListResponseDto;
import com.nexus.pdsw.service.CounselorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping("/api/v1/counselor")
@RequiredArgsConstructor
public class CounselorController {

    private final CounselorService counselorService;

    /*
     *  상담사 리스트 가져오기
     *
     *  @param String tenantId  상담사 소속 테넌트ID
     *  @param String roleId    상담사 역할 ID(1: 상담사, 2: 파트매니저, 3: 그룹매니저, 4: 테넌트메니저, 5: 시스템 메니저, 6: 전체)
     *  @return ResponseEntity<? super GetCounselorListResponseDto>
     */
    @GetMapping("/list")
    public ResponseEntity<? super GetCounselorListResponseDto> getCounselorList(
      @RequestParam(required = true, value = "tenantId") String tenantId,
      @RequestParam(required = true, value =  "roleId") String roleId
    ) {
      log.info("TenantId: {}", tenantId);
      ResponseEntity<? super GetCounselorListResponseDto> response = counselorService.getCounselorList(tenantId, roleId);
      return response;
    }

    /*
     *  상담사 상태정보 가져오기
     *
     *  @param String tenantId                            테넌트ID("0"이면 전체)
     *  @param PostCounselorListRequestDto requestBody    대상 상당원ID's
     *  @return ResponseEntity<? super GetCounselorStatusListResponseDto>
     */
    @PostMapping("/{tenantId}/state")
    public ResponseEntity<? super GetCounselorStatusListResponseDto> getCounselorStatusList(
      @PathVariable("tenantId") String tenantId,
      @RequestBody PostCounselorListRequestDto requestBody
    ) {
      ResponseEntity<? super GetCounselorStatusListResponseDto> response = counselorService.getCounselorStatusList(tenantId, requestBody);
      return response;
    }

    /*
     *  캠페인 할당 상담사정보 가져오기
     *
     *  @param String tenantId                            테넌트ID("0"이면 전체)
     *  @param PostCounselorListRequestDto requestBody    대상 상당원ID's
     *  @return ResponseEntity<? super GetCounselorInfoListResponseDto>
     */
    @PostMapping("/{tenantId}/counselorInfo")
    public ResponseEntity<? super GetCounselorInfoListResponseDto> getCounselorInfoList(
      @PathVariable("tenantId") String tenantId,
      @RequestBody PostCounselorListRequestDto requestBody
    ) {
      ResponseEntity<? super GetCounselorInfoListResponseDto> response = counselorService.getCounselorInfoList(tenantId, requestBody);
      return response;
    }
}
