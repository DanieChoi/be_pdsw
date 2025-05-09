/*------------------------------------------------------------------------------
 * NAME : OrganizationItem.java
 * DESC : 상담사 조직 개체 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/02/18  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationItem {

  private String centerId;                    //센터 ID
  private String centerName;                  //센터 명
  private List<TenantInfoItem> tenantInfo;    //테넌트 정보

  /*
   *  상담사 조직 리스트 반환 DTO 생성자
   * 
   *  @param RedisTemplate<String, Object> redisTemplate1   레디스 개체
   *  @param String tenantId                                로그인 상담사 소속 테넌트ID
   *  @param JSONObject jsonObjCenter                       센터 정보
  */
  private OrganizationItem(
    RedisTemplate<String, Object> redisTemplate1,
    String tenantId,
    JSONObject jsonObjCenter
  ) {
    
    JSONObject jsonObjCentereData = (JSONObject) jsonObjCenter.get("Data");
    
    this.centerId = jsonObjCentereData.get("center_id").toString();
    this.centerName = jsonObjCentereData.get("name").toString();

    this.tenantInfo = TenantInfoItem.getTenantList(redisTemplate1, tenantId, jsonObjCentereData.get("center_id").toString());

  }

  /*
   *  상담사 조직 리스트 반환 DTO로 변환하기
	 * 
   *  @param RedisTemplate<String, Object> redisTemplate1   레디스 개체
   *  @param String tenantId                                로그인 상담사 소속 테넌트ID
   *  @param JSONArray arrJsonCenter                        센터 정보
	*/
  public static List<OrganizationItem> getOrganizationList(
    RedisTemplate<String, Object> redisTemplate1,
    String tenantId,
    JSONArray arrJsonCenter
  ) {
    
    List<OrganizationItem> organizationList = new ArrayList<>();

    for (Object jsonCenter : arrJsonCenter) {
      JSONObject jsonObjCenter = (JSONObject) jsonCenter;
      OrganizationItem organizationInfo = new OrganizationItem(redisTemplate1, tenantId, jsonObjCenter);
      organizationList.add(organizationInfo);
    }

    return organizationList;
  }
}
