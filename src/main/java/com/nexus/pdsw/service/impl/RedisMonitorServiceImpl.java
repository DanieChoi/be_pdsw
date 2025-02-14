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

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.pdsw.dto.request.PostDialerChannelStatusInfoRequestDto;
import com.nexus.pdsw.dto.response.ResponseDto;
import com.nexus.pdsw.dto.response.monitor.PostDialerChannelStatusInfoResponseDto;
import com.nexus.pdsw.dto.response.monitor.GetProcessStatusInfoResponseDto;
import com.nexus.pdsw.dto.response.monitor.GetProgressInfoResponseDto;
import com.nexus.pdsw.dto.response.monitor.GetSendingProgressStatusResponseDto;
import com.nexus.pdsw.service.RedisMonitorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

  /*
   *  Dialer 채널 상태 정보 가져오기
   *  
   *  @param PostDialerChannelStatusInfoRequestDto requestDto     Dialer 장비ID's
   *  @return ResponseEntity<? super PostDialerChannelStatusInfoResponseDto>
   */
  @Override
  public ResponseEntity<? super PostDialerChannelStatusInfoResponseDto> getDialerChannelStatusInfo(
    PostDialerChannelStatusInfoRequestDto requestDto
  ) {

    List<Map<String, Object>> mapDialerChannelStatusList = new ArrayList<Map<String, Object>>();

    try {

      String redisKey = "";
      HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
      JSONParser jsonParser = new JSONParser();
      JSONArray arrJson = new JSONArray();

      //전체 Device에 대한 채널상태
      if (requestDto.getDeviceId().equals("0")) {        
        // Map<String, Object> bodyMap = new HashMap<>();
        WebClient webClient =
          WebClient
            .builder()
            .baseUrl("http://10.10.40.145:8010")
            .defaultHeaders(httpHeaders -> {
              httpHeaders.add(org.springframework.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
              httpHeaders.add("Session-Key", requestDto.getSessionKey());
            })
            .build();
      
        //API 요청
        Map<String, Object> apiDialer =
          webClient
            .post()
            .uri(uriBuilder ->
              uriBuilder
                .path("/pds/collections/dialing-device")
                .build()
            )
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        //장비 내역 가져오기 API 요청이 실패했을 때
        if (!apiDialer.get("result_code").equals(0)) {
          return PostDialerChannelStatusInfoResponseDto.notExistDialerDevice();
        }

        List<Map<String, Object>> mapDialerList = (List<Map<String, Object>>) apiDialer.get("result_data");

        for(Map<String, Object> mapDialer : mapDialerList) {

          redisKey = "monitor:dialer:" + mapDialer.get("device_id") + ":channel";

          Map<Object, Object> redisDialerChannelStatus = hashOperations.entries(redisKey);
          arrJson = (JSONArray) jsonParser.parse(redisDialerChannelStatus.values().toString());
          Map<String, Object> mapItem = null;

          for(Object jsonItem : arrJson) {
            try {
              mapItem = new ObjectMapper().readValue(jsonItem.toString(), Map.class);
            } catch (JsonMappingException e) {
              throw new RuntimeException(e);
            }
            mapDialerChannelStatusList.add(mapItem);
          }
        }
      //특정 Device에 대한 채널상태
      } else {

        redisKey = "monitor:dialer:" + requestDto.getDeviceId() + ":channel";

        Map<Object, Object> redisDialerChannelStatus = hashOperations.entries(redisKey);
        arrJson = (JSONArray) jsonParser.parse(redisDialerChannelStatus.values().toString());
        Map<String, Object> mapItem = null;

        for(Object jsonItem : arrJson) {
          try {
            mapItem = new ObjectMapper().readValue(jsonItem.toString(), Map.class);
          } catch (JsonMappingException e) {
            throw new RuntimeException(e);
          }
          mapDialerChannelStatusList.add(mapItem);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      ResponseDto.databaseError();
    }

    return PostDialerChannelStatusInfoResponseDto.success(mapDialerChannelStatusList);
  }

  /*
   *  캠페인별 진행정보 가져오기
   *  
   *  @param tenantId           테넌트ID
   *  @param campaignId         캠페인ID
   *  @return ResponseEntity<? super GetProgressInfoResponseDto>
   */
  @Override
  public ResponseEntity<? super GetProgressInfoResponseDto> getProgressInfo(
    String tenantId,
    String campaignId
  ) {

    List<Map<String, Object>> mapProgressInfoList = new ArrayList<Map<String, Object>>();

    try {
      
      HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
      JSONParser jsonParser = new JSONParser();
      JSONArray arrJson = new JSONArray();

      String redisKey = "monitor:tenant:" + tenantId + ":campaign:" + campaignId + ":statistics";

      Map<Object, Object> redisProgressInfo = hashOperations.entries(redisKey);
      arrJson = (JSONArray) jsonParser.parse(redisProgressInfo.values().toString());
      Map<String, Object> mapItem = null;

      for(Object jsonItem : arrJson) {
        try {
          mapItem = new ObjectMapper().readValue(jsonItem.toString(), Map.class);
        } catch (JsonMappingException e) {
          throw new RuntimeException(e);
        }
        mapProgressInfoList.add(mapItem);
      }

    } catch (Exception e) {
      e.printStackTrace();
      ResponseDto.databaseError();
    }

    return GetProgressInfoResponseDto.success(mapProgressInfoList);
  }

  /*
   *  발신진행상태 가져오기
   *  
   *  @param tenantId           테넌트ID
   *  @param campaignId         캠페인ID
   *  @return ResponseEntity<? super GetSendingProgressStatusResponseDto>
   */
  @Override
  public ResponseEntity<? super GetSendingProgressStatusResponseDto> getSendingProgressStatus(
    String tenantId,
    String campaignId
  ) {

    List<Map<String, Object>> mapSendingProgressStatusList = new ArrayList<Map<String, Object>>();

    try {
      
      HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
      JSONParser jsonParser = new JSONParser();
      JSONArray arrJson = new JSONArray();

      String redisKey = "monitor:tenant:" + tenantId + ":campaign:dial";

      Map<Object, Object> redisSendingProgressStatus = hashOperations.entries(redisKey);
      arrJson = (JSONArray) jsonParser.parse(redisSendingProgressStatus.values().toString());

      System.out.println(">>>" + arrJson.toString());
      Map<String, Object> mapItem = null;

      for(Object jsonItem : arrJson) {
        try {
          mapItem = new ObjectMapper().readValue(jsonItem.toString(), Map.class);
        } catch (JsonMappingException e) {
          throw new RuntimeException(e);
        }
        mapSendingProgressStatusList.add(mapItem);
      }
    } catch (Exception e) {
      e.printStackTrace();
      ResponseDto.databaseError();
    }
    return GetSendingProgressStatusResponseDto.success(mapSendingProgressStatusList);
  }
  
}
