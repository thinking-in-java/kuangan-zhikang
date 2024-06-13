package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhUserFoodGuide;

public interface YhUserFoodGuideMapper {
	
	List<YhUserFoodGuide> findAllInfo();
	
    YhUserFoodGuide findUserFoodGuiDeByid(Integer id);
	
    int deleteByPrimaryKey(Integer foodguideId);

    int insert(YhUserFoodGuide record);

    int insertSelective(YhUserFoodGuide record);

    YhUserFoodGuide selectByPrimaryKey(Integer foodguideId);

    int updateByPrimaryKeySelective(YhUserFoodGuide record);

    int updateByPrimaryKey(YhUserFoodGuide record);
    
    List<YhUserFoodGuide> selectAnalyze(Map<String, Object> map);
    
    int selectAnalyzeConut(Map<String, Object> map);
}