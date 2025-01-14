package com.nexus.pdsw.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeRepositoryTest {
  
  @Autowired
  private EmployeeRepository employeeRepository;

  @Test
  void selectAll() {
    employeeRepository.selectAll();
  }
}
