package com.nexus.pdsw.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

  //스레드 safe한 자료구조를 사용
  private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  public void save(String counselorId, SseEmitter emitter) {
    emitters.put(counselorId, emitter);
  }

  public void deleteByCounselorId(String counselorId) {
    emitters.remove(counselorId);
  }

  public SseEmitter get(String counselorId) {
    return emitters.get(counselorId);
  }
  
  public int getEmitterSize() {
    return emitters.size();
  }

  public boolean containKey(String counselorId) {
    return emitters.containsKey(counselorId);
  }
}
