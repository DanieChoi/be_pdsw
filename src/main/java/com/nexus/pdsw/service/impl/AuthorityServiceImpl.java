/*------------------------------------------------------------------------------
 * NAME : AuthorityServiceImpl.java
 * DESC : 권한 구현체
 * VER  : V1.0
 * PROJ : 웹 기반 PDS 구축 프로젝트
 * Copyright 2024 Dootawiz All rights reserved
 *------------------------------------------------------------------------------
 *                               MODIFICATION LOG
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                       DESCRIPTION
 * ----------  ------  -----------------------------------------------------------
 * 2025/03/18  최상원                       초기작성
 *------------------------------------------------------------------------------*/
package com.nexus.pdsw.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.nexus.pdsw.controller.AuthorityController;
import com.nexus.pdsw.dto.response.ResponseDto;
import com.nexus.pdsw.dto.response.authority.GetAvailableMenuListResponseDto;
import com.nexus.pdsw.entity.MenuByRoleEntity;
import com.nexus.pdsw.entity.RoleEntity;
import com.nexus.pdsw.repository.MenuByRoleRepository;
import com.nexus.pdsw.repository.RoleRepository;
import com.nexus.pdsw.service.AuthorityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

  private final MenuByRoleRepository menuByRoleRepository;
  private final RoleRepository roleRepository;

  /*
   *  사용가능한 메뉴 리스트 가져오기
   *
   *  @param int roleId    역할ID(1: 시스템관리자, 2: 테넌트관리자01, 3: 테넌트관리자02)
   *  @return ResponseEntity<? super GetAvailableMenuListResponseDto>
   */
  @Override
  public ResponseEntity<? super GetAvailableMenuListResponseDto> getAvailableMenuList(int roleId) {

    List<MenuByRoleEntity> menuListByRole = null;
    
    try {

      RoleEntity role = roleRepository.findByRoleId(roleId);
      log.info(">>>역할: {}", role.getRoleId());
      if (role == null) return GetAvailableMenuListResponseDto.notExistRole();

      menuListByRole = menuByRoleRepository.findByRole(role);

    } catch (Exception e) {
      e.printStackTrace();
      ResponseDto.databaseError();
    }

    if (menuListByRole == null) {
      return GetAvailableMenuListResponseDto.notExistMenu();
    } else {
      return GetAvailableMenuListResponseDto.success(menuListByRole);
    }
  }
  
}
