/*------------------------------------------------------------------------------
 * NAME : GetCounselorStatusListResponseDto.java
 * DESC : 상담사 상태정보 리스트 항목 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/01/18  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.response.counselor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nexus.pdsw.common.ResponseCode;
import com.nexus.pdsw.common.ResponseMessage;
import com.nexus.pdsw.dto.object.CounselorStatusListItem;
import com.nexus.pdsw.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class GetCounselorStatusListResponseDto extends ResponseDto {
  
  List<CounselorStatusListItem> counselorStatusList;
  
  /*  
   *  상담사 상태정보 리스트 가져오기(생성자)
   *  
   *  @param List<Map<String, String>> mapCounselorInfoList 반환할 상담사 리스트
   */
  private GetCounselorStatusListResponseDto(
    List<Map<String, Object>> mapCounselorStatusList
  ){

    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.counselorStatusList = CounselorStatusListItem.getCounselorStatusList(mapCounselorStatusList);
  }

  /*  
   *  상담사 상태정보 리스트 가져오기(성공)
   *  
   *  @param List<Map<String, String>> mapCounselorStatusList 반환할 상담사 상태 리스트
   *  @return ResponseEntity<GetCounselorStatusListResponseDto>
   */
  public static ResponseEntity<GetCounselorStatusListResponseDto> success(
    List<Map<String, Object>> mapCounselorStatusList
  ) {
    GetCounselorStatusListResponseDto result = new GetCounselorStatusListResponseDto(mapCounselorStatusList);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
