package com.yihe.system.mapper;

import com.yihe.system.domain.YhAssessConclusion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhAssessConclusionMapper {

    String findByState(@Param("score")int score, @Param("assess")String assess);

    YhAssessConclusion findByConcent(@Param("state")String state, @Param("assess")String assess);

    String findScoreL(Integer scorel);

    int findMaxScore(String assess);

    List<YhAssessConclusion> findBydivisor(String assess);
}
