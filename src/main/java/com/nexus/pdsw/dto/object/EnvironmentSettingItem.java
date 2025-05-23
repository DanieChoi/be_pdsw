/*------------------------------------------------------------------------------
 * NAME : EnvironmentSettingItem.java
 * DESC : 환경설정 항목 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/03/19  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentSettingItem {
  
  private int campaignListAlram;          //캠페인 리스트 잔량 부족시의 알람모드를 설정합니다.
  private int statisticsUpdateCycle;      //캠페인 통계를 서버로부터 가져오는 주기를 설정합니다.
  private int serverConnectionTime;       //서버와의 접속시간을 설정합니다.(프로그램 재시작시 적용)
  private int showChannelCampaignDayScop; //채널을 캠페인 모드로 할당시 화면에 보여주는 캠페인의 범위를 선택합니다.현재 날짜를 기준으로 설정한 값만큼의 범위안에서 캠페인을 보여줍니다.
  private int personalCampaignAlertOnly;  //본인 캠페인만 업링크 알림 여부
  private int useAlramPopup;              //알람 팝업 사용여부
  private int unusedWorkHoursCalc;        //업무 시간 계산 사용여부
  private String sendingWorkStartHours;   //발신업무시작시간
  private String sendingWorkEndHours;     //발신업무종료시간
  private String dayOfWeekSetting;        //요일을 설정할 수 있습니다.

	/*
   * 생성자
	 * 
	 * @param MenuByRoleEntity menuByRole
	*/

  /*
   * 환경설정 가져오기
	 * 
	 * @param       List<MenuByRoleEntity> menuListByRole
	 * @return      List<AvailableMenuItem> availableMenuList
	*/
}
