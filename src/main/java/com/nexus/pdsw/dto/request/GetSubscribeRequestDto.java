/*------------------------------------------------------------------------------
 * NAME : GetSubscribeRequestDto.java
 * DESC : SSE 실시간 이벤트 구독 요청 시 전달 DTO
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
package com.nexus.pdsw.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetSubscribeRequestDto {
  
  private String tenantId;    //상담원 소속 테넌트ID
  private String counselorId; //상담원 ID
  private String roleCd;      //상담원 역할코드
}
