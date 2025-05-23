/*------------------------------------------------------------------------------
 * NAME : AssignedSkillItem.java
 * DESC : 상담사 할당 스킬 리스트 항목 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/05/22  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
public class AssignedSkillItem {
  private String SkillId;
  private String SkillName;

  /*
   *  상담사에 할당된 스킬 리스트 반환 DTO 생성자
   * 
   *  @param Map<String, Object> mapAssignedSkill
  */
  private AssignedSkillItem(Map<String, Object> mapAssignedSkill) {
    this.SkillId = mapAssignedSkill.get("skill_id").toString();
    this.SkillName = mapAssignedSkill.get("skill_name").toString();
  }

  /*
   *  상담사에 할당된 스킬 리스트 반환 DTO로 변환하기
	 * 
   *  @param String baseUrl               기본 URL
   *  @param String sessionKey            세션 키
   *  @param String id                    반환할 상담사 소속 JSON 문자열열
	 *  @return List<AssignedSkillItem>
	*/
  public static List<AssignedSkillItem> getAssignedSkillList(
    String baseUrl,
    String sessionKey,
    String id
  ) {
    
    List<AssignedSkillItem> assignedSkillsList = new ArrayList<>();

    //WebClient로 API서버와 연결
    WebClient webClient =
      WebClient
        .builder()
        .baseUrl(baseUrl)
        .defaultHeaders(httpHeaders -> {
          httpHeaders.add(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
          httpHeaders.add("Session-Key", sessionKey);
        })
        .build();

    //API 호출 시 Request 개체 자료구조
    Map<String, Object> bodyMap = new HashMap<>();
    Map<String, Object> filterMap = new HashMap<>();
    String[] arrAgentId = null;
    List<Object> assignedSkillList = new ArrayList<>();

    if (id != null) {
      arrAgentId = new String[]{id};
    }

    filterMap.put("agent_id", arrAgentId);
    bodyMap.put("filter", filterMap);

    try {
      //상담원에 할당된 스킬 가져오기 API 요청
      Map<String, Object> apiAssignedSkills =
        webClient
          .post()
          .uri(uriBuilder ->
            uriBuilder
              .path("/pds/collections/agent-skill")
              .build()
          )
          .bodyValue(bodyMap)
          .retrieve()
          .bodyToMono(Map.class)
          .block();
 
      //상담사에 할당된 스킬이 존재하면
      if (apiAssignedSkills.get("result_data") != null) {
        List<Map<String, Object>> mapAssignedSkillsList = (List<Map<String, Object>>) apiAssignedSkills.get("result_data");

        for (Map<String, Object> mapAssignedSkill : mapAssignedSkillsList) {
          assignedSkillList.addAll((List<Object>) mapAssignedSkill.get("skill_id"));
        }
      }
    } catch (Exception e) {
      log.error("Error occurred while fetching assigned skills", e);
    }

    filterMap.clear();
    bodyMap.clear();

    int[] arrSkillId = new int[assignedSkillList.size()];
    int j = 0;

    //가져온 캠페인의 할당 상담원 가져오기
    for (Object campaign : assignedSkillList) {
      arrSkillId[j] = (int) campaign;
      j++;
    }

    filterMap.put("skill_id", arrSkillId);
    bodyMap.put("filter", filterMap);

    try {
      //스킬명 가져오기 API 요청
      Map<String, Object> apiAssignedSkillInfo =
        webClient
          .post()
          .uri(uriBuilder ->
            uriBuilder
              .path("/pds/collections/skill")
              .build()
          )
          .bodyValue(bodyMap)
          .retrieve()
          .bodyToMono(Map.class)
          .block();

      // log.info("apiAssignedSkillInfo : {}", apiAssignedSkillInfo.toString());

      //해당스킬ID의 스킬이 존재하면
      if (apiAssignedSkillInfo.get("result_data") != null) {
        List<Map<String, Object>> mapAssignedSkillInfoList = (List<Map<String, Object>>) apiAssignedSkillInfo.get("result_data");

        for (Map<String, Object> mapAssignedSkill : mapAssignedSkillInfoList) {
          assignedSkillsList.add(new AssignedSkillItem(mapAssignedSkill));
        }
      }

    } catch (Exception e) {
      log.error("Error occurred while fetching assigned skill info", e);
    }

    return assignedSkillsList;
  }

}
