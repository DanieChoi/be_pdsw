/*------------------------------------------------------------------------------
 * NAME : SendingProcessStatusItem.java
 * DESC : Redis 9번방의 캠페인 별 발신 상태정보 DTO
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/02/03  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.dto.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendingProcessStatusItem {

  private int TenantId;
  private int CampId;
  private int ReuseCnt;
  private int TotLstCnt;
  private int TotDialCnt;
  private int NonTTCT;
  private int SCCT;
  private int BUCT;
  private int NACT;
  private int FACT;
  private int ACCT;
  private int ETCT;
  private int TECT;
  private int LineStopCnt;
  private int CustomerOnHookCnt;
  private int DetectSilenceCnt;
  private int DialToneSilence;
  private int AgentConnect;
  private int OverDial;
  private int ABCT;
  private int AgentNoAnswerCnt;
  private int AgentBusyCnt;
  private int AgentDropCnt;
  private int CustomerDropCnt;
  private int NonServiceCnt;
  private int NoAgentCnt;
  private String CampListQuery;
  private int BlackList;
  private int DeleteAfterDial;
  private int DeleteBeforeDial;
  private int FirstCall;
  private int RetryCall;
  private int RecallCnt;
  private int TimeoutRecall;
  private int DialingCall;
  private int BlackListCall;
  private int FileIndex;
  private int NOGDeleteGL;
  private int NOGAddBL;
  private int NOGTimeOutCallback;
  private int NOGTimeContradictory;
  private int NOGBlockTime;
  private int NOGNotDialReady;
  private int NOGNotDialAgent;
  private int NOGAutoPopNotDial;
  private int NOGAutoPopNoAnswer;
  private int NOGAutoPopNoReady;
  private int NOGAutoPopFailMode;
  private int NOGAutoDialNoReady;
  private int NOGAutoDialFailMode;
  private int NOGAutoNoEmployeeId;
  private int DetectMachineLineBusy;
  private int DetectMachineNoanswer;
  private int DetectMachinePowerOff;
  private int DetectMachineRoaming;
  private int DetectMachineMissingNumber;
  private int DetectMachineEtc;

  /*
   *  Redis 9번방의 캠페인 별 발신 상태정보 DTO(생성자)
	 * 
   *  @param Map<String, Object> mapSendingProgressStatus  캠페인 별 발신 상태정보
	*/
  private SendingProcessStatusItem(
    Map<String, Object> mapSendingProgressStatus
  ) {
    
    this.TenantId = (int) mapSendingProgressStatus.get("TenantId");
    this.CampId = (int) mapSendingProgressStatus.get("CampId");
    if (mapSendingProgressStatus.get("ReuseCnt") == null) {
      this.ReuseCnt = 0;
    } else {
      this.ReuseCnt = (int) mapSendingProgressStatus.get("ReuseCnt");
    }
    this.TotLstCnt = (int) mapSendingProgressStatus.get("TotLstCnt");
    this.TotDialCnt = (int) mapSendingProgressStatus.get("TotDialCnt");
    this.NonTTCT = (int) mapSendingProgressStatus.get("NonTTCT");
    this.SCCT = (int) mapSendingProgressStatus.get("SCCT");
    this.BUCT = (int) mapSendingProgressStatus.get("BUCT");
    this.NACT = (int) mapSendingProgressStatus.get("NACT");
    this.FACT = (int) mapSendingProgressStatus.get("FACT");
    this.ACCT = (int) mapSendingProgressStatus.get("ACCT");
    this.ETCT = (int) mapSendingProgressStatus.get("ETCT");
    this.TECT = (int) mapSendingProgressStatus.get("TECT");
    this.LineStopCnt = (int) mapSendingProgressStatus.get("LineStopCnt");
    this.CustomerOnHookCnt = (int) mapSendingProgressStatus.get("CustomerOnHookCnt");
    this.DetectSilenceCnt = (int) mapSendingProgressStatus.get("DetectSilenceCnt");
    this.DialToneSilence = (int) mapSendingProgressStatus.get("DialToneSilence");
    this.AgentConnect = (int) mapSendingProgressStatus.get("AgentConnect");
    this.OverDial = (int) mapSendingProgressStatus.get("OverDial");
    this.ABCT = (int) mapSendingProgressStatus.get("ABCT");
    this.AgentNoAnswerCnt = (int) mapSendingProgressStatus.get("AgentNoAnswerCnt");
    this.AgentBusyCnt = (int) mapSendingProgressStatus.get("AgentBusyCnt");
    this.AgentDropCnt = (int) mapSendingProgressStatus.get("AgentDropCnt");
    this.CustomerDropCnt = (int) mapSendingProgressStatus.get("CustomerDropCnt");
    this.NonServiceCnt = (int) mapSendingProgressStatus.get("NonServiceCnt");
    this.NoAgentCnt = (int) mapSendingProgressStatus.get("NoAgentCnt");
    this.CampListQuery = mapSendingProgressStatus.get("CampListQuery").toString();
    this.BlackList = (int) mapSendingProgressStatus.get("BlackList");
    this.DeleteAfterDial = (int) mapSendingProgressStatus.get("DeleteAfterDial");
    this.DeleteBeforeDial = (int) mapSendingProgressStatus.get("DeleteBeforeDial");
    this.FirstCall = (int) mapSendingProgressStatus.get("FirstCall");
    this.RetryCall = (int) mapSendingProgressStatus.get("RetryCall");
    this.RecallCnt = (int) mapSendingProgressStatus.get("RecallCnt");
    this.TimeoutRecall = (int) mapSendingProgressStatus.get("TimeoutRecall");
    this.DialingCall = (int) mapSendingProgressStatus.get("DialingCall");
    this.BlackListCall = (int) mapSendingProgressStatus.get("BlackListCall");
    this.FileIndex = (int) mapSendingProgressStatus.get("FileIndex");
    this.NOGDeleteGL = (int) mapSendingProgressStatus.get("NOGDeleteGL");    
    this.NOGAddBL = (int) mapSendingProgressStatus.get("NOGAddBL");
    this.NOGTimeOutCallback = (int) mapSendingProgressStatus.get("NOGTimeOutCallback");
    this.NOGTimeContradictory = (int) mapSendingProgressStatus.get("NOGTimeContradictory");
    this.NOGBlockTime = (int) mapSendingProgressStatus.get("NOGBlockTime");
    this.NOGNotDialReady = (int) mapSendingProgressStatus.get("NOGNotDialReady");
    this.NOGNotDialAgent = (int) mapSendingProgressStatus.get("NOGNotDialAgent");
    this.NOGAutoPopNotDial = (int) mapSendingProgressStatus.get("NOGAutoPopNotDial");
    this.NOGAutoPopNoAnswer = (int) mapSendingProgressStatus.get("NOGAutoPopNoAnswer");
    this.NOGAutoPopNoReady = (int) mapSendingProgressStatus.get("NOGAutoPopNoReady");
    this.NOGAutoPopFailMode = (int) mapSendingProgressStatus.get("NOGAutoPopFailMode");
    this.NOGAutoDialNoReady = (int) mapSendingProgressStatus.get("NOGAutoDialNoReady");
    this.NOGAutoDialFailMode = (int) mapSendingProgressStatus.get("NOGAutoDialFailMode");
    this.NOGAutoNoEmployeeId = (int) mapSendingProgressStatus.get("NOGAutoNoEmployeeId");
    this.DetectMachineLineBusy = (int) mapSendingProgressStatus.get("DetectMachineLineBusy");
    this.DetectMachineNoanswer = (int) mapSendingProgressStatus.get("DetectMachineNoanswer");
    this.DetectMachinePowerOff = (int) mapSendingProgressStatus.get("DetectMachinePowerOff");
    this.DetectMachineRoaming = (int) mapSendingProgressStatus.get("DetectMachineRoaming");
    this.DetectMachineMissingNumber = (int) mapSendingProgressStatus.get("DetectMachineMissingNumber");
    this.DetectMachineEtc = (int) mapSendingProgressStatus.get("DetectMachineEtc");

  }

  /*
   *  Redis 9번방의 캠페인 별 발신 상태정보 DTO로 변환하기
	 * 
   *  @param List<Map<String, String>> mapSendingProgressStatusList  캠페인 별 발신 상태정보 리스트
	 *  @return List<SendingProcessStatusItem>
	*/
  public static List<SendingProcessStatusItem> getSendingProgressStatus(
    List<Map<String, Object>> mapSendingProgressStatusList
  ) {

    List<SendingProcessStatusItem> sendingProcessStatusList = new ArrayList<>();

    for(Map<String, Object> mapSendingProgressStatus : mapSendingProgressStatusList) {
      SendingProcessStatusItem sendingProcessStatusItem = new SendingProcessStatusItem(mapSendingProgressStatus);
      sendingProcessStatusList.add(sendingProcessStatusItem);
    }

    return sendingProcessStatusList;
  }
}
