package com.nexus.pdsw.entity;

import com.nexus.pdsw.dto.object.EmployeeGroup;
import com.nexus.pdsw.dto.object.Skill;

import lombok.Getter;

@Getter
public class Employee {
  private String center_id;                 //센터 ID
  private String tenant_id;                 //테넌트 ID
  private EmployeeGroup employee_group_id;  //상담원 그룹 ID
  private String id;                        //상담원 ID
  private String name;                      //상담원 이름
  private String passwd;                    //상담원 비밀번호
  private String media_login_id;            //로그인 ID
  private String blend_kind;                //블랜딩 종류(1: 인바운드, 2: 아웃바운드, 3: 블랜드)
  private String role_id;                   //권한 ID
  private Skill skill;                      //상담원 보유 스킬
  private String only_work;                 //상담원 업무유형
}