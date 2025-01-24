/*------------------------------------------------------------------------------
 * NAME : PostSubscribeResponseDto.java
 * DESC : 상담사 로그인 시 채널구독 후 반환 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/01/23  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.response.counselor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nexus.pdsw.common.ResponseCode;
import com.nexus.pdsw.common.ResponseMessage;
import com.nexus.pdsw.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class PutSubscribeResponseDto extends ResponseDto {

  /*  
   *  상담사 로그인 시 채널 구독하기(생성자)
   *  
   *  @param JSONArray arrJsonCenter                        센터 정보
   *  @param JSONArray arrJsonTenant                        테넌트 정보
   *  @param JSONArray arrJsonGroup                         부서(그룹) 정보
   *  @param JSONArray arrJsonTeam                          부서(팀) 정보
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
   */
  private PutSubscribeResponseDto() {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
  }

  /*  
   *  상담사 로그인 시 채널 구독하기(성공)
   *  
   *  @param JSONArray arrJsonCenter                        센터 정보
   *  @param JSONArray arrJsonTenant                        테넌트 정보
   *  @param JSONArray arrJsonGroup                         부서(그룹) 정보
   *  @param JSONArray arrJsonTeam                          부서(팀) 정보
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
   */
  public static ResponseEntity<PutSubscribeResponseDto> success() {

    PutSubscribeResponseDto result = new PutSubscribeResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
