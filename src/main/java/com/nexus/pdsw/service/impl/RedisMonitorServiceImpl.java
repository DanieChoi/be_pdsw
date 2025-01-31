/*------------------------------------------------------------------------------
 * NAME : RedisMonitorServiceImpl.java
 * DESC : Redis 9번방에서 모니터링 정보 가져오기 구현체
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/01/31  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.pdsw.dto.response.ResponseDto;
import com.nexus.pdsw.dto.response.monitor.GetProcessStatusInfoResponseDto;
import com.nexus.pdsw.service.RedisMonitorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisMonitorServiceImpl implements RedisMonitorService {

  private final RedisTemplate<String, Object> redisTemplate;

  /*
   *  타 시스템 프로세스 상태정보 가져오기
   *  
   *  @return ResponseEntity<? super GetProcessStatusInfoResponseDto>
   */
  @Override
  public ResponseEntity<? super GetProcessStatusInfoResponseDto> getProcessStatusInfo() {

    List<Map<String, Object>> mapMonitorProcessList = new ArrayList<Map<String, Object>>();

    try {
      
      // System.out.println(redisTemplate.keys("*"));
      HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
      JSONParser jsonParser = new JSONParser();
      JSONArray arrJsonMonitorProcess = new JSONArray();

      String redisKey = "monitor:process";

      Map<Object, Object> redisMonitorProcess = hashOperations.entries(redisKey);
      arrJsonMonitorProcess = (JSONArray) jsonParser.parse(redisMonitorProcess.values().toString());
      Map<String, Object> mapMonitorProcess = null;

      for(Object jsonMonitorProcess : arrJsonMonitorProcess) {
        try {
          mapMonitorProcess = new ObjectMapper().readValue(jsonMonitorProcess.toString(), Map.class);
        } catch (JsonMappingException e) {
          throw new RuntimeException(e);
        }
        mapMonitorProcessList.add(mapMonitorProcess);
      }

    } catch (Exception e) {
      e.printStackTrace();
      ResponseDto.databaseError();
    }
    return GetProcessStatusInfoResponseDto.success(mapMonitorProcessList);
  }
  
}
