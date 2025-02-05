/*------------------------------------------------------------------------------
 * NAME : RedisMessageService.java
 * DESC : Redis의 채널을 구독하고 이벤트를 발행하는 기능
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/02/04  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.service;

import com.nexus.pdsw.dto.object.NotificationDto;

public interface RedisMessageService {
  
  /*
   *  Redis 채널 구독
   *  
   *  @param String channel   채널정보
   *  @return void
   */
  public void subscribe(String channel);

  /*
   *  Redis 채널 배포
   *  
   *  @param String channel                   채널정보
   *  @param NotificationDto notificationDto  배포 메시지
   *  @return void
   */
  public void publish(String channel, NotificationDto notificationDto);

  /*
   *  Redis 구독 채널 삭제
   *  
   *  @param String channel                   채널정보
   *  @return void
   */
  public void removeSubscribe(String channel);

}
