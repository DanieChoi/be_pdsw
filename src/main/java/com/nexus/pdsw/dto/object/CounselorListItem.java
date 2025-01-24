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
 * 2025/01/21  최상원                       센터명, 테넌트명, 그룹명, 팀명 추가
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CounselorListItem {
  private String centerId;                                      //센터 ID
  private String centerName;                                    //센터 명
  private String tenantId;                                      //테넌트 ID
  private String tenantName;                                    //테넌트 명
  private List<CounselorAffiliation> counselorAffiliation;      //상담사 소속부서 정보리스트
  private String affiliationGroupId;                            //상담사 소속부서(그룹)ID
  private String affiliationGroupName;                          //상담사 소속부서(그룹)명
  private String affiliationTeamId;                             //상담사 소속부서(팀)ID
  private String affiliationTeamName;                           //상담사 소속부서(팀)명
  private String counselorId;                                   //상담사 ID
  private String counselorname;                                 //상담사 이름
  private String blendKind;                                     //블랜딩 종류(1: 인바운드, 2: 아웃바운드, 3: 블랜드)

  /*
   *  상담사 리스트 반환 DTO 생성자
   * 
   *  @param JSONArray arrJsonCenter              센터 정보
   *  @param JSONArray arrJsonTenant              테넌트 정보
   *  @param JSONArray arrJsonGroup               부서(그룹) 정보
   *  @param JSONArray arrJsonTeam                          부서(팀) 정보
   *  @param Map<String, Object> mapCounselorInfo 반환할 상담사 정보
  */
  private CounselorListItem(
    JSONArray arrJsonCenter,
    JSONArray arrJsonTenant,
    JSONArray arrJsonGroup,
    JSONArray arrJsonTeam,
    Map<String, Object> mapCounselorInfo
  ) {
    
    ObjectMapper objectMapper = new ObjectMapper();

    this.centerId = (String) mapCounselorInfo.get("center_id");

    for (Object jsonCenter : arrJsonCenter) {
      JSONObject jsonObjCenter = (JSONObject) jsonCenter;
      if (jsonObjCenter.get("CENTER").toString().equals(this.centerId)) {
       JSONObject jsonObjCentereData = (JSONObject) jsonObjCenter.get("Data");
       this.centerName = jsonObjCentereData.get("name").toString();
       break;
      }
    }

    this.tenantId = mapCounselorInfo.get("tenant_id").toString();

    for (Object jsonTenant : arrJsonTenant) {
      JSONObject jsonObjTenant = (JSONObject) jsonTenant;
      if (jsonObjTenant.get("TENANT").toString().equals(this.tenantId)) {
       JSONObject jsonObjTenantData = (JSONObject) jsonObjTenant.get("Data");
       this.tenantName = jsonObjTenantData.get("name").toString();
       break;
      }
    }

    Map<String, Object> mapCounselorAffiliation = objectMapper.convertValue(mapCounselorInfo.get("employee_group_id"), Map.class);
    mapCounselorAffiliation.forEach((key, value) -> {
      if (key.equals("1")) {
        this.affiliationGroupId = (String) value;
      } else {
        this.affiliationTeamId = (String) value;
      }
    });

    for (Object jsonGroup : arrJsonGroup) {
      JSONObject jsonObjGroup = (JSONObject) jsonGroup;
       if (jsonObjGroup.get("GROUP").toString().equals(this.affiliationGroupId)) {
        JSONObject jsonObjGroupData = (JSONObject) jsonObjGroup.get("Data");
        this.affiliationGroupName = jsonObjGroupData.get("name").toString();
        break;
      }
    }

    for (Object jsonTeam : arrJsonTeam) {
      JSONObject jsonObjTeam = (JSONObject) jsonTeam;
       if (jsonObjTeam.get("TEAM").toString().equals(this.affiliationTeamId)) {
        JSONObject jsonObjTeamData = (JSONObject) jsonObjTeam.get("Data");
        if (jsonObjTeamData.get("tenant_id").toString().equals(this.tenantId)) {
          this.affiliationTeamName = jsonObjTeamData.get("name").toString();
          break;
        }
      }
    }

    this.counselorAffiliation = CounselorAffiliation.getCounselorAffiliationList(mapCounselorAffiliation, this.affiliationGroupName, this.affiliationTeamName, this.tenantId);
    this.counselorId = (String) mapCounselorInfo.get("id");
    this.counselorname = (String) mapCounselorInfo.get("name");
    this.blendKind = (String) mapCounselorInfo.get("blend_kind");
  }

  /*
   *  상담사 리스트 반환 DTO로 변환하기
	 * 
   *  @param JSONArray arrJsonCenter                        센터 정보
   *  @param JSONArray arrJsonTenant                        테넌트 정보
   *  @param JSONArray arrJsonGroup                         부서(그룹) 정보
   *  @param JSONArray arrJsonTeam                          부서(팀) 정보
   *  @param List<Map<String, Object>> mapCounselorInfoList 반환할 상담사 리스트
	 *  @return List<CounselorListItem>
	*/
  public static List<CounselorListItem> getCounselorList(
    JSONArray arrJsonCenter,
    JSONArray arrJsonTenant,
    JSONArray arrJsonGroup,
    JSONArray arrJsonTeam,
    List<Map<String, Object>> mapCounselorInfoList
  ) {
    
    List<CounselorListItem> counselorList = new ArrayList<>();

    for (Map<String, Object> mapCounselorInfo : mapCounselorInfoList) {
      CounselorListItem counselorInfo = new CounselorListItem(arrJsonCenter, arrJsonTenant, arrJsonGroup, arrJsonTeam, mapCounselorInfo); 
      counselorList.add(counselorInfo);
    }

    return counselorList;
  }
}
