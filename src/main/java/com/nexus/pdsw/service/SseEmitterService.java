package com.nexus.pdsw.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.nexus.pdsw.repository.EmitterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SseEmitterService {
  
  private final EmitterRepository emitterRepository;

  public SseEmitter createEmitter(String counselorId, SseEmitter emitter) {

    // 현재 저장된 emitter의 수를 조회하여 자동 삭제를 확인
    // System.out.println("현재 저장된 emitter의 수: " + emitterRepository.getEmitterSize());
    emitterRepository.save(counselorId, emitter);

    emitter.onCompletion(()->{
      // 만일 emitter가 만료되면 삭제한다.
      // System.out.println(counselorId);
      emitterRepository.deleteByCounselorId(counselorId);
    });

    emitter.onTimeout(()->{
      emitterRepository.get(counselorId).complete();
    });
    return emitter;
  }

  public SseEmitter getEmitter(String counselorId){
    return emitterRepository.get(counselorId);
  }

  public boolean containKey(String counselorId){
    return emitterRepository.containKey(counselorId);
  }
}
