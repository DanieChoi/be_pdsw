package com.nexus.pdsw.dto.object;

import lombok.Getter;

@Getter
public class NotificationDto {
  
  private String message; // 전송할 메세지 내용
  private String sender;  // 메세지 발신자
}
