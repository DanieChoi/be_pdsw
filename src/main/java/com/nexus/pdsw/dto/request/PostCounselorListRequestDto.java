/*------------------------------------------------------------------------------
 * NAME : PostCounselorListRequestDto.java
 * DESC : 상담원 목록 가져오기 호출 시 대상 전달 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/02/03  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCounselorListRequestDto {

  // private String tenantId;    //선택 테넌트ID("0"이면 전체)
  // private String campaignId;  //선택 캠페인ID("0"이면 전체)
  private String counselors;  //,로 구분된 상담사 ID
  
}
