/*------------------------------------------------------------------------------
 * NAME : CounselorServiceImpl.java
 * DESC : 상담사 내역 불러오기 구현체
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
package com.nexus.pdsw.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.pdsw.dto.response.ResponseDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorListResponseDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorStatusListResponseDto;
import com.nexus.pdsw.service.CounselorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CounselorServiceImpl implements CounselorService {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  /*  
   *  상담사 리스트 가져오기
   *  
   *  @param String tenantId  상담사 소속 테넌트ID
   *  @param String roleId    상담사 역할 ID(1: 상담사, 2: 파트매니저, 3: 그룹매니저, 4: 테넌트메니저, 5: 시스템 메니저, 6: 전체)
   *  @return ResponseEntity<? super GetCounselorListResponseDto>
   */
  @Override
  public ResponseEntity<? super GetCounselorListResponseDto> getCounselorList(
    String tenantId,
    String roleId
  ) {

    List<Map<String, Object>> mapCounselorInfoList = new ArrayList<Map<String, Object>>();

    try {
      
      String redisKey = "";
      HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

      Map<Object, Object> redisCounselorList = new HashMap<>();

      Map<String, Object> mapCounselorInfo = null;

      JSONArray arrJsonCounselor = new JSONArray();      
      JSONParser jsonParser = new JSONParser();

      // 로그인 사용자의 역할ID에 따라 다른 상담사 리스트를 반환한다.
      switch (roleId) {
        //테넌트 매니저저
        case "4":
          redisKey = "master.employee-1-" + tenantId;
          redisCounselorList = hashOperations.entries(redisKey);

          arrJsonCounselor = (JSONArray) jsonParser.parse(redisCounselorList.values().toString());
          // arrJsonCounselor.add(redisCounselorList.values().toString());

          for (int i = 0; i < arrJsonCounselor.size(); ++i) {
            JSONObject counselor = (JSONObject) arrJsonCounselor.get(i);
            JSONObject counselorInfo = (JSONObject) counselor.get("Data");
            try {
              mapCounselorInfo = new ObjectMapper().readValue(counselorInfo.toString(), Map.class);
            } catch (JsonParseException e) {
              e.printStackTrace();
            } catch (JsonMappingException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }
            mapCounselorInfoList.add(mapCounselorInfo);
          }
              
          break;

        //시스템 매니저
        case "5":
        //전체
        case "6":
          Map<Object, Object> redisTenantList = hashOperations.entries("master.tenant-1");

          for (Object tenantKey : redisTenantList.keySet()) {
            redisKey = "master.employee-1-" + tenantKey;
            redisCounselorList = hashOperations.entries(redisKey);

            arrJsonCounselor = (JSONArray) jsonParser.parse(redisCounselorList.values().toString());
            arrJsonCounselor.add(redisCounselorList.values().toString());

            for (int i = 0; i < arrJsonCounselor.size() - 1; ++i) {
              JSONObject counselor = (JSONObject) arrJsonCounselor.get(i);
              JSONObject counselorInfo = (JSONObject) counselor.get("Data");
              try {
                mapCounselorInfo = new ObjectMapper().readValue(counselorInfo.toString(), Map.class);
              } catch (JsonParseException e) {
                e.printStackTrace();
              } catch (JsonMappingException e) {
                e.printStackTrace();
              } catch (IOException e) {
                e.printStackTrace();
              }
              mapCounselorInfoList.add(mapCounselorInfo);
            }
          }
          
          break;
      
        default:
          break;
      }

    } catch (Exception e) {
            e.printStackTrace();
            ResponseDto.databaseError();
    }
    return GetCounselorListResponseDto.success(mapCounselorInfoList);
  }

  /*
   *  상담사 상태정보 가져오기
   *  
   *  @param String tenantId        테넌트ID
   *  @param String counselorIds    상당원ID's
   *  @return ResponseEntity<? super GetCounselorStatusListResponseDto>
   */
  @Override
  public ResponseEntity<? super GetCounselorStatusListResponseDto> getCounselorStatusList(
    String tenantId,
    String counselorIds
  ) {

    List<Map<String, Object>> mapCounselorStatusList = new ArrayList<Map<String, Object>>();

    try {

      String[] arrCounselorId = counselorIds.split(",");

      String redisKey = "";
      String redisCounselorIdx = "";
      HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
      Map<Object, Object> redisCounselorStatusList = new HashMap<>();

      JSONArray arrJsonCounselorState = new JSONArray();      
      JSONParser jsonParser = new JSONParser();

      String strCounselorId = "";
      String strStateCode = "";

      Map<String, Object> mapCounselorState = new HashMap<>();

      //사이드 바(상담원)에서 선택한 항목이 센터인 경우
      if (tenantId.equals("0")) {
          Map<Object, Object> redisTenantList = hashOperations.entries("master.tenant-1");
          for (Object tenantKey : redisTenantList.keySet()) {
            redisCounselorStatusList = hashOperations.entries(redisKey);

          }
        
      } else {
        redisKey = "st.employee.state-1-" + tenantId;
        redisCounselorStatusList = hashOperations.entries(redisKey);

        arrJsonCounselorState = (JSONArray) jsonParser.parse(redisCounselorStatusList.values().toString());

        for (String counselorId : arrCounselorId){

          for (Object jsonCounselorState : arrJsonCounselorState) {

            JSONObject jsonObjCounselorState = (JSONObject) jsonCounselorState;

            strCounselorId = jsonObjCounselorState.get("EMPLOYEE").toString();

            if (counselorId.equals(strCounselorId)) {
              
              JSONObject jsonObjCounselorStateData = (JSONObject) jsonObjCounselorState.get("Data");
              strStateCode = jsonObjCounselorStateData.get("state").toString();

              if (strStateCode.equals("203") || strStateCode.equals("204") || strStateCode.equals("205") || strStateCode.equals("206")) {

                try {
                  mapCounselorState = new ObjectMapper().readValue(jsonObjCounselorStateData.toString(), Map.class);
                } catch (JsonParseException e) {
                  e.printStackTrace();
                } catch (JsonMappingException e) {
                  e.printStackTrace();
                } catch (IOException e) {
                  e.printStackTrace();
                }
                mapCounselorStatusList.add(mapCounselorState);
                break;
              }
            }
          }
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      ResponseDto.databaseError();
    }
    return GetCounselorStatusListResponseDto.success(mapCounselorStatusList);
  }
}
