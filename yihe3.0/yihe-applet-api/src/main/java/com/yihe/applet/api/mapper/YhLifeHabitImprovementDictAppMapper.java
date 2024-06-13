package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhLifeHabitImprovementDictApp;

public interface YhLifeHabitImprovementDictAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitImprovementDictApp record);

    int insertSelective(YhLifeHabitImprovementDictApp record);

    YhLifeHabitImprovementDictApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitImprovementDictApp record);

    int updateByPrimaryKey(YhLifeHabitImprovementDictApp record);
    
    YhLifeHabitImprovementDictApp selectimprovement(String userCode);
    
    int selectimprovementCount();
    
    List<YhLifeHabitImprovementDictApp> selectHabitCodeDict(String userCode);
}