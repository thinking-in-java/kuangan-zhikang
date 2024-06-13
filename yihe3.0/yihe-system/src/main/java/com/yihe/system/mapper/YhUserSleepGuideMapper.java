package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhUserSleepGuide;

public interface YhUserSleepGuideMapper {
    int deleteByPrimaryKey(Integer sleepGuideId);

    int insert(YhUserSleepGuide record);

    int insertSelective(YhUserSleepGuide record);

    YhUserSleepGuide selectByPrimaryKey(Integer sleepGuideId);

    int updateByPrimaryKeySelective(YhUserSleepGuide record);

    int updateByPrimaryKey(YhUserSleepGuide record);
    
    List<YhUserSleepGuide> selectAnalyzeSleep(Map<String, Object> map);
    
    int selectAnalyzeSleepConut(Map<String, Object> map);
}