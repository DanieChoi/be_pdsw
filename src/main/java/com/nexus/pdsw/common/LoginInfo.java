package com.nexus.pdsw.common;

public class LoginInfo {
  private static final LoginInfo INStANCE = new LoginInfo();

  private String counselorId;

  private LoginInfo() {}

  public static LoginInfo getInstance() {
    return INStANCE;
  }

  public void setCounselorId(String counselorId) {
    this.counselorId = counselorId;
  }

  public String getCounselorId() {
    return counselorId;
  }
}
