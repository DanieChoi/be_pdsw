/*------------------------------------------------------------------------------
 * NAME : CounselorServiceImpl.java
 * DESC : 상담사 관련 구현체
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/01/15  최상원                       초기작성
 * 2025/01/21  최상원                       상담사 내역 가져오기에 센터명, 테넌트명, 그룹명, 팀명 추가
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.pdsw.dto.request.PostCounselorListRequestDto;
import com.nexus.pdsw.dto.response.ResponseDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorInfoListResponseDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorListResponseDto;
import com.nexus.pdsw.dto.response.counselor.GetCounselorStatusListResponseDto;
import com.nexus.pdsw.service.CounselorService;

import jakarta.websocket.OnClose;

@Service
public class CounselorServiceImpl implements CounselorService {

  @Qualifier("1")
  private final RedisTemplate<String, Object> redisTemplate1;

  public CounselorServiceImpl(
    @Qualifier("1") RedisTemplate<String, Object> redisTemplate1
  ) {
    this.redisTemplate1 = redisTemplate1;
  }

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

    JSONArray arrJsonCenter = new JSONArray();
    JSONArray arrJsonTenant = new JSONArray();
    JSONArray arrJsonGroup = new JSONArray();
    JSONArray arrJsonTeam = new JSONArray();

    try {

      String redisKey = "";
      String stateRedisKey = "";
      HashOperations<String, Object, Object> hashOperations = redisTemplate1.opsForHash();
      JSONParser jsonParser = new JSONParser();
  
      //센터정보
      redisKey = "master.center";
      Map<Object, Object> redisCenter = hashOperations.entries(redisKey);
      arrJsonCenter = (JSONArray) jsonParser.parse(redisCenter.values().toString());

      //테넌트정보
      JSONObject jsonObjCenter = new JSONObject();
      Map<Object, Object> redisTenant = new HashMap<>();

      for (Object jsonCenter : arrJsonCenter) {

        jsonObjCenter = (JSONObject) jsonCenter;
        redisKey = "master.tenant-" + jsonObjCenter.get("CENTER").toString();

        redisTenant = hashOperations.entries(redisKey);
        arrJsonTenant.addAll((JSONArray) jsonParser.parse(redisTenant.values().toString()));
      }

      //부서(그룹)정보
      JSONObject jsonObjTenant = new JSONObject();
      Map<Object, Object> redisGroup = new HashMap<>();

      for (Object jsonCenter : arrJsonCenter) {

        jsonObjCenter = (JSONObject) jsonCenter;

        for (Object jsonTenant : arrJsonTenant) {

          jsonObjTenant = (JSONObject) jsonTenant;
          redisKey = "master.group-emp-" + jsonObjCenter.get("CENTER").toString() + "-" + jsonObjTenant.get("TENANT").toString();

          redisGroup = hashOperations.entries(redisKey);
          arrJsonGroup.addAll((JSONArray) jsonParser.parse(redisGroup.values().toString()));
        }
      }

      //부서(팀)정보
      Map<Object, Object> redisTeam = new HashMap<>();

      for (Object jsonCenter : arrJsonCenter) {

        jsonObjCenter = (JSONObject) jsonCenter;

        for (Object jsonTenant : arrJsonTenant) {

          jsonObjTenant = (JSONObject) jsonTenant;
          redisKey = "master.team-emp-" + jsonObjCenter.get("CENTER").toString() + "-" + jsonObjTenant.get("TENANT").toString();

          redisTeam = hashOperations.entries(redisKey);
          arrJsonTeam.addAll((JSONArray) jsonParser.parse(redisTeam.values().toString()));
        }
      }

      Map<Object, Object> redisCounselorList = new HashMap<>();
      Map<String, Object> mapCounselorInfo = null;
      JSONArray arrJsonCounselor = new JSONArray();

      //로그인 사용자의 역할ID에 따라 다른 상담사 리스트를 반환한다.
      switch (roleId) {
        //테넌트 매니저저
        case "4":
          redisKey = "master.employee-1-" + tenantId;
          redisCounselorList = hashOperations.entries(redisKey);

          arrJsonCounselor = (JSONArray) jsonParser.parse(redisCounselorList.values().toString());

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

            for (Object jsonCounselor : arrJsonCounselor) {
              JSONObject counselor = (JSONObject) jsonCounselor;
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
    return GetCounselorListResponseDto.success(arrJsonCenter, arrJsonTenant, arrJsonGroup, arrJsonTeam, mapCounselorInfoList);
  }

  /*
   *  상담사 상태정보 가져오기
   *  
   *  @param String tenantId                            테넌트ID("0"이면 전체)
   *  @param PostCounselorListRequestDto requestBody    대상 상당원ID's
   *  @return ResponseEntity<? super GetCounselorStatusListResponseDto>
   */
  @Override
  public ResponseEntity<? super GetCounselorStatusListResponseDto> getCounselorStatusList(
    String tenantId,
    PostCounselorListRequestDto requestBody
  ) {

    List<Map<String, Object>> mapCounselorStatusList = new ArrayList<Map<String, Object>>();

    try {

      String[] arrCounselorId = requestBody.getCounselors().split(",");

      String redisKey = "";
      String redisCounselorIdx = "";
      HashOperations<String, Object, Object> hashOperations = redisTemplate1.opsForHash();
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

  /*
   *  캠페인 할당 상담사정보 가져오기
   *  
   *  @param String tenantId                            테넌트ID("0"이면 전체)
   *  @param PostCounselorListRequestDto requestBody    대상 상당원ID's
   *  @return ResponseEntity<? super GetCounselorInfoListResponseDto>
   */
  @Override
  public ResponseEntity<? super GetCounselorInfoListResponseDto> getCounselorInfoList(
    String tenantId,
    PostCounselorListRequestDto requestBody
  ) {

    List<Map<String, Object>> mapCounselorInfoList = new ArrayList<Map<String, Object>>();

    try {

      String[] arrCounselorId = requestBody.getCounselors().split(",");

      String redisKey = "";
      HashOperations<String, Object, Object> hashOperations = redisTemplate1.opsForHash();
      Map<Object, Object> redisCounselorList = new HashMap<>();
      JSONArray arrJson = new JSONArray();      
      JSONParser jsonParser = new JSONParser();

      //센터 내 모든 상담원 대상으로 정보요구 시
      if (tenantId.equals("0")) {
          Map<Object, Object> redisTenantList = hashOperations.entries("master.tenant-1");
          for (Object tenantKey : redisTenantList.keySet()) {
            redisKey = "master.employee-1-" + tenantKey;
            redisCounselorList = hashOperations.entries(redisKey);
            
            arrJson = (JSONArray) jsonParser.parse(redisCounselorList.values().toString());

          }
        
      } else {
        redisKey = "master.employee-1-" + tenantId;
        redisCounselorList = hashOperations.entries(redisKey);

        arrJson = (JSONArray) jsonParser.parse(redisCounselorList.values().toString());
        Map<String, Object> mapItem = null;

        for (String counselorId : arrCounselorId){
          for (Object jsonItem : arrJson) {
            JSONObject jsonObj = (JSONObject) jsonItem;

            //해당 테넌트에 소속된 상담사 중 대상 상담사의 ID와 동일하면
            if (counselorId.equals(jsonObj.get("EMPLOYEE").toString())) {
              JSONObject jsonObjData = (JSONObject) jsonObj.get("Data");

              try {
                mapItem = new ObjectMapper().readValue(jsonObjData.toString(), Map.class);
              } catch (JsonMappingException e) {
                throw new RuntimeException(e);
              }
              mapCounselorInfoList.add(mapItem);
              break;
            }
          }
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      ResponseDto.databaseError();
    }

    return GetCounselorInfoListResponseDto.success(mapCounselorInfoList);
  }
}
