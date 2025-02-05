/*------------------------------------------------------------------------------
 * NAME : EventLogEntity.java
 * DESC : 로그기록 엔티티
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/02/05  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import com.nexus.pdsw.common.AesEncDecConverter;
import com.nexus.pdsw.dto.request.PostEventLogRequestDto;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="event_log")
public class EventLogEntity {
  
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long uidx;                //일련번호
  private int tenantId;             //테넌트ID
  private String employeeId;        //상담원ID
  private String userHost;          //접속ip, hostname등
  private String queryId;           //web query id
  private String queryType;         //이벤트타입 I:insert, U:update, D:delete, R:select, A:자원변경
  private String activation;        //행위 로그
  @Convert(converter = AesEncDecConverter.class)
  private String description;       //부가정보
  private int successFlag;          //성공여부 1 : 성공 0 : 실패 또는 DB 에러코드
  private LocalDateTime eventTime;  //이벤트일시
  private String eventName;         //이벤트명 login, logout, 테이블명, employee.postion_level
  private int queryRows;            //조회건수
  private String targetId;          //대상자ID
  private int userSessionType;      //사용자접속타입 0 : 로컬PC 1 : VDI 또는 재택 연결 시
  private int exportFlag;           //export실행여부 1 : export 실행
  private String memo;              //메모 ex)파일다운사유 등
  private String updateEmployeeId;  //수정상담원ID
  private LocalDateTime updateTime; //수정일시

	/*
   * 생성자
   * @param String emitterKey   Emitter 키
	 * 
	*/
  public EventLogEntity(PostEventLogRequestDto dto) {

    this.tenantId = dto.getTenantId();
    this.employeeId = dto.getEmployeeId();
    this.userHost = dto.getUserHost();
    this.queryId = dto.getQueryId();
    this.queryType = dto.getQueryType();
    this.activation = dto.getActivation();
    this.description = dto.getDescription();
    this.successFlag = dto.getSuccessFlag();
    this.eventTime = LocalDateTime.now();
    this.eventName = dto.getEventName();
    this.queryRows = dto.getQueryRows();
    this.targetId = dto.getTargetId();
    this.userSessionType = dto.getUserSessionType();
    this.exportFlag = dto.getExportFlag();
    this.memo = dto.getMemo();
    this.updateEmployeeId = dto.getUpdateEmployeeId();
    this.updateTime = LocalDateTime.now();
  }
}
