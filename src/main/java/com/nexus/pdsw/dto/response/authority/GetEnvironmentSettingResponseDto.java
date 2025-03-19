/*------------------------------------------------------------------------------
 * NAME : GetEnvironmentSettingResponseDto.java
 * DESC : 환경설정 항목 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/03/19  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.response.authority;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nexus.pdsw.dto.object.EnvironmentSettingItem;

import lombok.Getter;

@Getter
public class GetEnvironmentSettingResponseDto {

  EnvironmentSettingItem environmentSetting;
  
  /*  
   *  환경설정 가져오기(생성자)
   *  
   *  @param List<MenuByRoleEntity> menuListByRole  반환할 메뉴 리스트
   */
  private GetEnvironmentSettingResponseDto() {
    // super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
  }

  /*  
   *  환경설정 가져오기(성공)
   *  
   *  @param List<MenuByRoleEntity> menuListByRole  반환할 메뉴 리스트
   *  @return ResponseEntity<GetAvailableMenuListResponseDto>
   */
  public static ResponseEntity<GetEnvironmentSettingResponseDto> success() {

    GetEnvironmentSettingResponseDto result = new GetEnvironmentSettingResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
