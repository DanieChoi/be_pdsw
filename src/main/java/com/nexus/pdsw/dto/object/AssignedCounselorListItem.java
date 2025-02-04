/*------------------------------------------------------------------------------
 * NAME : AssignedCounselorListItem.java
 * DESC : 캠페인 별 할당된 상담사 리스트 항목 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/02/04  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssignedCounselorListItem {
  
  private String affiliationGroupId;    //상담사 소속부서(그룹)ID
  private String affiliationTeamId;     //상담사 소속부서(팀)ID
  private String counselorEmplNum;      //상담사 사번
  private String counselorId;           //상담사 ID
  private String counselorname;         //상담사 이름

  /*
   *  캠페인 별 할당된 상담사 리스트 반환 DTO(생성자)
   * 
   *  @param Map<String, Object> mapCounselorInfo 반환할 상담사 정보
  */
  private AssignedCounselorListItem(
    Map<String, Object> mapCounselorInfo
  ) {

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> mapCounselorAffiliation = objectMapper.convertValue(mapCounselorInfo.get("employee_group_id"), Map.class);
    mapCounselorAffiliation.forEach((key, value) -> {
      if (key.equals("1")) {
        this.affiliationGroupId = (String) value;
      } else {
        this.affiliationTeamId = (String) value;
      }
    });

    this.counselorEmplNum = (String) mapCounselorInfo.get("id");
    this.counselorId = (String) mapCounselorInfo.get("media_login_id");
    this.counselorname = (String) mapCounselorInfo.get("name");

  }

  /*
   *  캠페인 별 할당된 상담사 리스트 반환하기
	 * 
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
	 *  @return List<AssignedCounselorListItem>
	*/
  public static List<AssignedCounselorListItem> getAssignedCounselorList(
    List<Map<String, Object>> mapCounselorInfoList
  ) {
    
    List<AssignedCounselorListItem> counselorList = new ArrayList<>();

    for (Map<String, Object> mapCounselorInfo : mapCounselorInfoList) {
      AssignedCounselorListItem counselorInfo = new AssignedCounselorListItem(mapCounselorInfo); 
      counselorList.add(counselorInfo);
    }

    return counselorList;

  }

}
