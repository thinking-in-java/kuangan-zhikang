package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserFoodWeeklyWeb;

public interface YhUserFoodWeeklyWebMapper {

	int findFoodWeekISNot(@Param("usercode")String usercode,@Param("strTime")String strTime,@Param("endTime")String endTime);
	
	int findUserNotifyISNot(@Param("usercode")String usercode,@Param("endTime")String endTime);
	List<YhUserFoodWeeklyWeb> findFoodWeekLyByUserCode(String usercode);
	
    int deleteByPrimaryKey(Integer wid);

    int insert(YhUserFoodWeeklyWeb record);

    int insertSelective(YhUserFoodWeeklyWeb record);

    YhUserFoodWeeklyWeb selectByPrimaryKey(Integer wid);

    int updateByPrimaryKeySelective(YhUserFoodWeeklyWeb record);

    int updateByPrimaryKey(YhUserFoodWeeklyWeb record);
}