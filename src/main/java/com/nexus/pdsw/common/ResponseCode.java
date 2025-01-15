/*------------------------------------------------------------------------------
 * NAME : ResponseCode.java
 * DESC : 반환 코드 정의
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
package com.nexus.pdsw.common;

public interface ResponseCode {
  
  // HTTP Status 200
  String SUCCESS = "SU";

  // HTTP Status 400(Bad Request)
  String VALIDATION_FAILED = "VF";

  // HTTP Status 401(Unauthorized)

  // HTTP Status 403(Forbidden)
  String NOT_MATCHED_PASSWORD = "NMP";                //비밀번호가 일지하지 않을때
  String EXCEEDING_3TIMES = "E3T";                    //비밀번호 실패 3회초과

  // HTTP Status 500(Internal Server Error)
  String DATABASE_ERROR = "DBE";
}
