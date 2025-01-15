package com.nexus.pdsw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class RedisController {
  
  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @GetMapping("/redis/{key}")
  public ResponseEntity<?> getRedisKey(@PathVariable String key) throws ParseException {

    HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
    Map<Object, Object> employees = hashOperations.entries(key);

    Map<String, Object> map = null;
    List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

    JSONParser jsonParser = new JSONParser();
    
    JSONArray arrJsonCounselor = (JSONArray) jsonParser.parse(employees.values().toString());
    arrJsonCounselor.add(employees.values().toString());

    for (int i = 0; i < arrJsonCounselor.size() - 1; ++i) {
      JSONObject counselor = (JSONObject) arrJsonCounselor.get(i);
      JSONObject counselorInfo = (JSONObject) counselor.get("Data");

      try {
        map = new ObjectMapper().readValue(counselorInfo.toString(), Map.class);
      } catch (JsonParseException e) {
        e.printStackTrace();
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      listMap.add(map);
    }

    return new ResponseEntity<>(employees.values(), HttpStatus.OK);
  }
}