package com.yihe.applet.api.mapper;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserFoodWeekly;

public interface YhUserFoodWeeklyMapper {
	
	
    int deleteByPrimaryKey(Integer wid);

    int insert(YhUserFoodWeekly record);

    int insertSelective(YhUserFoodWeekly record);

    YhUserFoodWeekly selectByPrimaryKey(Integer wid);
    
    YhUserFoodWeekly findUserWeekLyInfoByUsercode(@Param("usercode")String usercode,@Param("startTime")String startTime,@Param("endTime")String endTime);

    int updateByPrimaryKeySelective(YhUserFoodWeekly record);

    int updateByPrimaryKey(YhUserFoodWeekly record);
}