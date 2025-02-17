/*------------------------------------------------------------------------------
 * NAME : CounselorService.java
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
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.service;

import org.springframework.http.ResponseEntity;

import com.nexus.pdsw.dto.request.PostCounselorListRequestDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorInfoListResponseDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorListResponseDto;
import com.nexus.pdsw.dto.response.counselor.PostCounselorStatusListResponseDto;

public interface CounselorService {
 
  /*  
   *  상담사 리스트 가져오기
   *  
   *  @param String tenantId  상담사 소속 테넌트ID
   *  @param String roleId    상담사 역할 ID(1: 상담사, 2: 파트매니저, 3: 그룹매니저, 4: 테넌트메니저, 5: 시스템 메니저, 6: 전체)
   *  @return ResponseEntity<? super GetCounselorListResponseDto>
   */
  ResponseEntity<? super GetCounselorListResponseDto> getCounselorList(String tenantId, String roleId);

  /*
   *  상담사 상태정보 가져오기
   *  
   *  @param PostCounselorListRequestDto requestBody    전달 DTO
   *  @return ResponseEntity<? super PostCounselorStatusListResponseDto>
   */
  ResponseEntity<? super PostCounselorStatusListResponseDto> getCounselorStatusList(PostCounselorListRequestDto requestBody);

  /*
   *  캠페인 할당 상담사정보 가져오기
   *  
   *  @param PostCounselorListRequestDto requestBody    전달 매개변수 개체 DTO
   *  @return ResponseEntity<? super GetCounselorInfoListResponseDto>
   */
  ResponseEntity<? super GetCounselorInfoListResponseDto> getCounselorInfoList(PostCounselorListRequestDto requestBody);
}
