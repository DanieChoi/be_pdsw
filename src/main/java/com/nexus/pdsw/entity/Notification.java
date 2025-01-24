package com.nexus.pdsw.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "Note")
@Data
public class Notification {
  
    @Id
    String counselorId;
    String type;
    String date;
    String name;
}    
