/*------------------------------------------------------------------------------
 * NAME : GetCounselorInfoListResponseDto.java
 * DESC : 캠페인 할당상담사 정보 리스트 항목 DTO
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
package com.nexus.pdsw.dto.response.counselor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nexus.pdsw.common.ResponseCode;
import com.nexus.pdsw.common.ResponseMessage;
import com.nexus.pdsw.dto.object.AssignedCounselorListItem;
import com.nexus.pdsw.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class GetCounselorInfoListResponseDto extends ResponseDto {
  
  private List<AssignedCounselorListItem> assignedCounselorList;

  /*  
   *  캠페인 할당상담사 정보 리스트 가져오기(생성자)
   *  
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
   */
  private GetCounselorInfoListResponseDto(
    List<Map<String, Object>> mapCounselorInfoList
  ) {

    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.assignedCounselorList = AssignedCounselorListItem.getAssignedCounselorList(mapCounselorInfoList);
  }

  /*  
   *  캠페인 할당상담사 정보 리스트 가져오기(성공)
   *  
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
   *  @return ResponseEntity<GetCounselorListResponseDto>
   */
  public static ResponseEntity<GetCounselorInfoListResponseDto> success(
    List<Map<String, Object>> mapCounselorInfoList
  ) {

    GetCounselorInfoListResponseDto result = new GetCounselorInfoListResponseDto(mapCounselorInfoList);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  /*  
   *  캠페인 할당상담사 정보 리스트 가져오기(API 인증 세션키가 없을 경우)
   *  
   *  @return ResponseEntity<GetDialerChannelStatusInfoResponseDto>
   */
  public static ResponseEntity<ResponseDto> notExistSessionKey() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_SESSIONKEY, ResponseMessage.NOT_EXISTED_SESSIONKEY);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }
}
