/*------------------------------------------------------------------------------
 * NAME : NotificationDto.java
 * DESC : 실시간 이벤트 전달 DTO
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
package com.nexus.pdsw.dto.object;

import lombok.Getter;

@Getter
public class NotificationDto {
  
  private String kind;
  private String command;
  private String announce;
  private Object data;
}
