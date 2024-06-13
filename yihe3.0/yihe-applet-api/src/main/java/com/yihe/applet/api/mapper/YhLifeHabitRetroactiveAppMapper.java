package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhLifeHabitRetroactiveApp;

public interface YhLifeHabitRetroactiveAppMapper { 
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitRetroactiveApp record);

    int insertSelective(YhLifeHabitRetroactiveApp record);

    YhLifeHabitRetroactiveApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitRetroactiveApp record);

    int updateByPrimaryKey(YhLifeHabitRetroactiveApp record);
    
    List<YhLifeHabitRetroactiveApp> selectAll(@Param("userCode") String userCode);
}