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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CounselorItem {
  private String counselorId;                                   //상담사 ID
  private String counselorLoginId;                              //상담사 로그인 ID
  private String counselorname;                                 //상담사 이름
  private String blendKind;                                     //블랜딩 종류(1: 인바운드, 2: 아웃바운드, 3: 블랜드)

  /*
   *  상담사 리스트 반환 DTO 생성자
   * 
   *  @param JSONObject objCounselorData 상담사 정보
  */
  private CounselorItem(
    JSONObject objCounselorData
  ) {
    
    // JSONObject objCounselorData = (JSONObject) mapCounselor.get("Data");

    this.counselorId = (String) objCounselorData.get("id");
    this.counselorLoginId = (String) objCounselorData.get("media_login_id");
    this.counselorname = (String) objCounselorData.get("name");
    this.blendKind = (String) objCounselorData.get("blend_kind");
  }

  /*
   *  상담사 리스트 반환 DTO로 변환하기
	 * 
   *  @param RedisTemplate<String, Object> redisTemplate1   레디스 개체
   *  @param String centerId                                센터ID
   *  @param String tenantId                                테넌트ID
   *  @param String groupId                                 그룹ID
   *  @param String teamId                                  팀ID
	 *  @return List<CounselorListItem>
	*/
  public static List<CounselorItem> getCounselorList(
    RedisTemplate<String, Object> redisTemplate1,
    String centerId,
    String tenantId,
    String groupId,
    String teamId
  ) {
    
    List<CounselorItem> counselorList = new ArrayList<>();

    String redisKey = "master.employee-" + centerId + "-" + tenantId;

    HashOperations<String, Object, Object> hashOperations = redisTemplate1.opsForHash();

    JSONParser jsonParser = new JSONParser();

    Map<String, Object> mapCounselor = new HashMap<>();

    ScanOptions scanOptions = ScanOptions.scanOptions().match( groupId + "-" + teamId + "*" ).build();

    //상담원정보
    Cursor<Map.Entry<Object, Object>> redisCounselor = hashOperations.scan(redisKey, scanOptions);
    while ( redisCounselor.hasNext() ) {
      Map.Entry<Object, Object> next = redisCounselor.next();

      try {
        mapCounselor = (Map<String, Object>) jsonParser.parse(next.getValue().toString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      
      JSONObject objCounselorData = (JSONObject) mapCounselor.get("Data");
      
      //only_work가 call인 상담원만 가져오는 부분은 차후 환경설정 변수로 처리할 예정(2025-02-25 최상원)
      if (!objCounselorData.get("media_login_id").equals("NULL") && objCounselorData.get("only_work").equals("call")) {
        CounselorItem counselorInfo = new CounselorItem(objCounselorData); 
        counselorList.add(counselorInfo);          
      }

    }

    return counselorList;
  }
}
