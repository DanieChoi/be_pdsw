/*------------------------------------------------------------------------------
 * NAME : CounselorAffiliation.java
 * DESC : 상담사 소속부서 정보 항목 DTO
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CounselorAffiliation {
  private String affiliationDepth;        //소속 Depth
  private String affiliatedDepartmentId;  //소속 부서 아이디

  /*
   *  상담사 소속부서리스트 반환 DTO 생성자
	 * 
   *  @param Map<String, Object> mapCounselorAffiliation 반환할 상담사 소속 부서 정보
	*/
  private CounselorAffiliation (
    String key,
    Object value
  ) {
    this.affiliationDepth = key;
    this.affiliatedDepartmentId = (String) value;
  }

  /*
   *  상담사 소속 부서정보 반환 DTO로 변환하기
	 * 
   *  @param Map<String, Object> mapCounselorAffiliation 반환할 상담사 소속 정보 Map
	 *  @return List<CounselorAffiliation>
	*/
  public static List<CounselorAffiliation> getCounselorAffiliationList(
    Map<String, Object> mapCounselorAffiliation
  ) {
    
    List<CounselorAffiliation> counselorAffiliationList = new ArrayList<>();

    mapCounselorAffiliation.forEach((key, value) -> {
      CounselorAffiliation counselorAffiliation = new CounselorAffiliation(key, value);
      counselorAffiliationList.add(counselorAffiliation);
    });

    return counselorAffiliationList;
  }
}
