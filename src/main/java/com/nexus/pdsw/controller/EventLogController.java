/*------------------------------------------------------------------------------
 * NAME : EventLogController.java
 * DESC : 이벤트 로그 기록
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
package com.nexus.pdsw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.pdsw.dto.request.PostEventLogRequestDto;
import com.nexus.pdsw.dto.response.eventLog.PostEventLogResponseDto;
import com.nexus.pdsw.service.EventLogService;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api_upds/v1/log")
@RequiredArgsConstructor
@RestController
public class EventLogController {

  private final EventLogService eventLogService;
  private static final Logger logger = LoggerFactory.getLogger(EventLogController.class);
  private static final String localIP = NetworkUtils.getLocalIPAddress(); // 서버 내부 IP

  /*
   * 이벤트 로그 저장하기
   * 
   * @param PostEventLogRequestDto requestBody 이벤트 로그 전달 DTO
   * 
   * @return ResponseEntity<? super PostEventLogResponseDto>
   */
  @PostMapping("/save")
  public ResponseEntity<? super PostEventLogResponseDto> saveEventLog(
      @RequestBody PostEventLogRequestDto requestBody,
      HttpServletRequest request) {
    logger.info("요청 처리됨 - 서버 내부 IP: {}, 클라이언트 IP: {}", localIP, getLocalIPv4());
    ResponseEntity<? super PostEventLogResponseDto> response = eventLogService.saveEventLog(requestBody);
    return response;
  }

  // 서버 내부 IP 가져오기
  public class NetworkUtils {
    public static String getLocalIPAddress() {
      try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
          NetworkInterface nic = interfaces.nextElement();
          for (InetAddress address : Collections.list(nic.getInetAddresses())) {
            if (!address.isLoopbackAddress() && address.isSiteLocalAddress()) {
              return address.getHostAddress();
            }
          }
        }
      } catch (SocketException e) {
        e.printStackTrace();
      }
      return "UNKNOWN";
    }
  }

  // 클라이언트 IP 가져오기
  // private String getClientIP(HttpServletRequest request) {
  // String xfHeader = request.getHeader("X-Forwarded-For");
  // return xfHeader != null ? xfHeader.split(",")[0] : request.getRemoteAddr();
  // }

  public static String getLocalIPv4() {
    try {
      Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

      while (interfaces.hasMoreElements()) {
        NetworkInterface networkInterface = interfaces.nextElement();

        // 조건: 루프백이 아니고, 활성화된 인터페이스
        if (!networkInterface.isUp() || networkInterface.isLoopback() || networkInterface.isVirtual()) {
          continue;
        }

        // IP 주소 목록 조회
        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
        while (addresses.hasMoreElements()) {
          InetAddress address = addresses.nextElement();

          // IPv4 주소만 선택 (IPv6 제외)
          if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
            return address.getHostAddress(); // 예: 192.168.0.105
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "IP Not Found";
  }
}
