package com.nexus.pdsw.dto.object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignedSkills {
  private String SkillId;

  /*
   *  상담사 소속 부서리스트 반환 DTO로 변환하기
	 * 
   *  @param String strPossessedSkills 반환할 상담사 소속 JSON 문자열열
	 *  @return List<CounselorAffiliation>
	*/
  public static List<AssignedSkills> getPossessedSkillsList(
    String strPossessedSkills
  ) {
    
    List<AssignedSkills> possessedSkillsList = new ArrayList<>();

    try {
      Map<String, Object> mapPossessedSkills = new ObjectMapper().readValue(strPossessedSkills, Map.class);
    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return possessedSkillsList;
  }

}
