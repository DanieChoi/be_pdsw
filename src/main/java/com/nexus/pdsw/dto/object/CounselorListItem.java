/*------------------------------------------------------------------------------
 * NAME : CounselorListItem.java
 * DESC : 상담사리스트 항목 DTO
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
package com.nexus.pdsw.dto.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CounselorListItem {
  private String centerId;                                      //센터 ID
  private String tenantId;                                      //테넌트 ID
  private List<CounselorAffiliation> counselorAffiliation;      //상담사 소속부서 정보리스트
  private String AffiliationGroupId;                            //상담사 소속부서(그룹)
  private String AffiliationTeamId;                             //상담사 소속부서(팀)
  private String counselorId;                                   //상담사 ID
  private String counselorname;                                 //상담사 이름
  private String blendKind;                                     //블랜딩 종류(1: 인바운드, 2: 아웃바운드, 3: 블랜드)
  private List<PossessedSkills> possessedSkillsList;            //상담사 보유 스킬 리스트
  private String onlyWork;                                      //상담사 업무유형

  /*
   *  상담사 리스트 반환 DTO 생성자
	 * 
   *  @param Map<String, Object> mapCounselorInfo 반환할 상담사 정보
	*/
  private CounselorListItem(
    Map<String, Object> mapCounselorInfo
  ) {
    
    ObjectMapper objectMapper = new ObjectMapper();

    this.centerId = (String) mapCounselorInfo.get("center_id");
    this.tenantId = (String) mapCounselorInfo.get("tenant_id");

    Map<String, Object> mapCounselorAffiliation = objectMapper.convertValue(mapCounselorInfo.get("employee_group_id"), Map.class);
    mapCounselorAffiliation.forEach((key, value) -> {
      if (key.equals("1")) {
        this.AffiliationGroupId = (String) value;
      } else {
        this.AffiliationTeamId = (String) value;
      }
    });
    this.counselorAffiliation = CounselorAffiliation.getCounselorAffiliationList(mapCounselorAffiliation);
    this.counselorId = (String) mapCounselorInfo.get("id");
    this.counselorname = (String) mapCounselorInfo.get("name");
    this.blendKind = (String) mapCounselorInfo.get("blend_kind");
    // this.possessedSkillsList = PossessedSkills.getPossessedSkillsList(mapCounselorInfo.get("skill"));
    this.onlyWork = (String) mapCounselorInfo.get("onlyWork");
  }

  /*
   *  상담사 리스트 반환 DTO로 변환하기
	 * 
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
	 *  @return List<CounselorListItem>
	*/
  public static List<CounselorListItem> getCounselorList(
    List<Map<String, Object>> mapCounselorInfoList
  ) {
    
    List<CounselorListItem> counselorList = new ArrayList<>();

    for (Map<String, Object> mapCounselorInfo : mapCounselorInfoList) {
      CounselorListItem counselorInfo = new CounselorListItem(mapCounselorInfo); 
      counselorList.add(counselorInfo);
    }

    return counselorList;
  }
}
