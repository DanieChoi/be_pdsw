/*------------------------------------------------------------------------------
 * NAME : PostDialerChannelStatusInfoRequestDto.java
 * DESC : 장비별 채널상태내역 가져오기 호출 시 대상장비 전달 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/02/10  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDialerChannelStatusInfoRequestDto {
  
  private String deviceIds;  //,로 구분된 Dialer 장비 ID's
}
