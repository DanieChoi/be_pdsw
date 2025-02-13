/*------------------------------------------------------------------------------
 * NAME : GetSendingProgressStatusResponseDto.java
 * DESC : 발신진행상태 가져오기 항목 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/02/13  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.response.monitor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nexus.pdsw.common.ResponseCode;
import com.nexus.pdsw.common.ResponseMessage;
import com.nexus.pdsw.dto.object.SendingProgressStatusItem;
import com.nexus.pdsw.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class GetSendingProgressStatusResponseDto extends ResponseDto {
  
  private List<SendingProgressStatusItem> sendingProgressStatusList;

  /*  
   *  발신진행상태 가져오기(생성자)
   *  
   *  @param List<Map<String, Object>> mapSendingProgressStatusList  발신진행상태 리스트
   */
  private GetSendingProgressStatusResponseDto(
    List<Map<String, Object>> mapSendingProgressStatusList
  ) {

    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.sendingProgressStatusList = SendingProgressStatusItem.getSendingProgressStatus(mapSendingProgressStatusList);
  }

  /*  
   *  발신진행상태 가져오기(성공)
   *  
   *  @param List<Map<String, Object>> mapSendingProgressStatusList  발신진행상태 리스트
   *  @return ResponseEntity<GetSendingProgressStatusResponseDto>
   */
  public static ResponseEntity<GetSendingProgressStatusResponseDto> success(
    List<Map<String, Object>> mapSendingProgressStatusList
  ) {
    GetSendingProgressStatusResponseDto result = new GetSendingProgressStatusResponseDto(mapSendingProgressStatusList);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
