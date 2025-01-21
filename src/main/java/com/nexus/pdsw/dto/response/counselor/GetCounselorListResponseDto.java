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
 * 2025/01/21  최상원                       센터명, 테넌트명, 그룹명, 팀명 추가
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.response.counselor;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.data.redis.core.RedisTemplate;
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
   *  @param JSONArray arrJsonCenter                        센터 정보
   *  @param JSONArray arrJsonTenant                        테넌트 정보
   *  @param JSONArray arrJsonGroup                         부서(그룹) 정보
   *  @param JSONArray arrJsonTeam                          부서(팀) 정보
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
   */
  private GetCounselorListResponseDto(
    JSONArray arrJsonCenter,
    JSONArray arrJsonTenant,
    JSONArray arrJsonGroup,
    JSONArray arrJsonTeam,
    List<Map<String, Object>> mapCounselorInfoList
  ) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.counselorList = CounselorListItem.getCounselorList(arrJsonCenter, arrJsonTenant, arrJsonGroup, arrJsonTeam, mapCounselorInfoList);
  }

  /*  
   *  상담사 리스트 가져오기(성공)
   *  
   *  @param JSONArray arrJsonCenter                        센터 정보
   *  @param JSONArray arrJsonTenant                        테넌트 정보
   *  @param JSONArray arrJsonGroup                         부서(그룹) 정보
   *  @param JSONArray arrJsonTeam                          부서(팀) 정보
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
   *  @return ResponseEntity<GetCounselorListResponseDto>
   */
  public static ResponseEntity<GetCounselorListResponseDto> success(
    JSONArray arrJsonCenter,
    JSONArray arrJsonTenant,
    JSONArray arrJsonGroup,
    JSONArray arrJsonTeam,
    List<Map<String, Object>> mapCounselorInfoList
  ) {
    GetCounselorListResponseDto result = new GetCounselorListResponseDto(arrJsonCenter, arrJsonTenant, arrJsonGroup, arrJsonTeam, mapCounselorInfoList);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
