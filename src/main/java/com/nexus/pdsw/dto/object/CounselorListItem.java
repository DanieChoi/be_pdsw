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
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CounselorListItem {
  private String centerId;                            //센터 ID
  private String tenantId;                            //테넌트 ID
  private CounselorAffiliation counselorAffiliation;  //상담사 소속부서 정보
  private String counselorId;                         //상담사 ID
  private String counselorname;                       //상담사 이름
  private String blendKind;                           //블랜딩 종류(1: 인바운드, 2: 아웃바운드, 3: 블랜드)
  private PossessedSkills possessedSkills;            //상담사 보유 스킬
  private String onlyWork;                            //상담사 업무유형

  /*
   *  상담사 리스트 반환 DTO 생성자
	 * 
   *  @param Map<String, Object> mapCounselorInfo 반환할 상담사 정보
	*/
  private CounselorListItem(
    Map<String, Object> mapCounselorInfo
  ) {
    this.centerId = (String) mapCounselorInfo.get("center_id");
    this.tenantId = (String) mapCounselorInfo.get("tenant_id");
    this.counselorId = (String) mapCounselorInfo.get("id");
    this.counselorname = (String) mapCounselorInfo.get("name");
    this.blendKind = (String) mapCounselorInfo.get("blend_kind");
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
