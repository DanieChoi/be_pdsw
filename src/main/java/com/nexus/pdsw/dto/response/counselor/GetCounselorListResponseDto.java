/*------------------------------------------------------------------------------
 * NAME : GetCounselorListResponseDto.java
 * DESC : 상담사 리스트 항목 DTO
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
package com.nexus.pdsw.dto.response.counselor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nexus.pdsw.common.ResponseCode;
import com.nexus.pdsw.common.ResponseMessage;
import com.nexus.pdsw.dto.object.CounselorListItem;
import com.nexus.pdsw.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class GetCounselorListResponseDto extends ResponseDto  {

  private List<CounselorListItem> counselorList;

  /*  
   *  상담사 리스트 가져오기(생성자)
   *  
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
   */
  private GetCounselorListResponseDto(
    List<Map<String, Object>> mapCounselorInfoList
  ) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.counselorList = CounselorListItem.getCounselorList(mapCounselorInfoList);
  }

  /*  
   *  상담사 리스트 가져오기(성공)
   *  
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
   *  @return ResponseEntity<? super GetCounselorListResponseDto>
   */
  public static ResponseEntity<GetCounselorListResponseDto> success(
    List<Map<String, Object>> mapCounselorInfoList
  ) {
    GetCounselorListResponseDto result = new GetCounselorListResponseDto(mapCounselorInfoList);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
