/*------------------------------------------------------------------------------
 * NAME : ResponseMessage.java
 * DESC : 반환 메시지 정의
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

public interface ResponseMessage {
  
  // HTTP Status 200
  String SUCCESS = "Success";

  // HTTP Status 400(Bad Request)
  String VALIDATION_FAILED = "Validation failed.";

  // HTTP Status 401(Unauthorized)

  // HTTP Status 403(Forbidden)
  String NOT_MATCHED_PASSWORD = "Miss Matched Your Password.";
  String EXCEEDING_3TIMES = "The number of password failures exceeded 3 times. Please contact your administrator to log in.";

  // HTTP Status 500(Internal Server Error)
  String DATABASE_ERROR = "Database error.";
}
