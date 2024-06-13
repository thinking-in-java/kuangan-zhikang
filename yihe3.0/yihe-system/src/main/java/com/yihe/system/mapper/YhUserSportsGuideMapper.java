package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhUserSportsGuide;

public interface YhUserSportsGuideMapper {
    int deleteByPrimaryKey(Integer sportsGuideId);

    int insert(YhUserSportsGuide record);

    int insertSelective(YhUserSportsGuide record);

    YhUserSportsGuide selectByPrimaryKey(Integer sportsGuideId);

    int updateByPrimaryKeySelective(YhUserSportsGuide record);

    int updateByPrimaryKey(YhUserSportsGuide record);
    
    List<YhUserSportsGuide> selectAnalyzeSports(Map<String, Object> map);
    
    int selectAnalyzeSportsConut(Map<String, Object> map);
}