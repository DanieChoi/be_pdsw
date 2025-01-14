package com.nexus.pdsw.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.pdsw.dto.object.ResEmployee;
import com.nexus.pdsw.entity.Employee;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
  
  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper;

  // public void create(
  //   String center_id,
  //   String tenant_id,
  //   Object employee_group_id,
  //   String id,
  //   String name,
  //   String passwd,
  //   String media_login_id,
  //   String blend_kind,
  //   String role_id,
  //   Object skill,
  //   String only_work
  // ) {
  //   Employee employee = Employee.builder()
  //     .center_id(center_id)
  //     .tenant_id(tenant_id)
  //     .employee_group_id(employee_group_id)
  //     .id(id)
  //     .name(name)
  //     .passwd(passwd)
  //     .media_login_id(media_login_id)
  //     .blend_kind(blend_kind)
  //     .role_id(role_id)
  //     .skill(skill)
  //     .only_work(only_work)
  //     .build();

  //   setRedis(center_id, tenant_id, employee);
  // }

  public void selectAll() {

    ResEmployee resEmployees = getRedisValue("master.employee-1-1", ResEmployee.class);

    System.out.println(">> 출력: " + resEmployees);
  }

  /**
   * value 값을 직렬화하여 redis에 저장
   */
  private void setRedis(String center_id, String tenant_id, Object value) {

    String key = "master.employee-" + center_id + "-" + tenant_id;
    
    try {
      redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 직렬화되어 저장된 value 값을 redis에서 조회 후 class type에 맞게 변환
   */
  private <T> T getRedisValue(String key, Class<T> valueClass) {

    String value = redisTemplate.opsForValue().get(key);
    // HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
    // Map<Object, Object> employees = hashOperations.entries(key);

    if (!StringUtils.hasText(value)) {
        return null;
    }

    try {
        return objectMapper.readValue(value, valueClass);
    } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
    }
  }

  /**
   * 직렬화되어 저장된 value 값을 redis에서 조회 후 class type에 맞게 변환
   */
  private <T> List<T> getRedisValue(List<String> keys, Class<T> valueClass) {
    List<String> values = redisTemplate.opsForValue().multiGet(keys);
    if (values.size() < 1) {
      return new ArrayList<>();
    }
    return values
      .stream()
      .map(value -> {
        try {
          return objectMapper.readValue(value, valueClass);
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      })
      .collect(Collectors.toList());
  }  
}
