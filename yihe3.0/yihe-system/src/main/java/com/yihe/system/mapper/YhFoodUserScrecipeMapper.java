package com.yihe.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhFoodUserScrecipe;

public interface YhFoodUserScrecipeMapper {
    String findEnrolinfoByFood(String userocde);
    int modifyEnrolinfo(String userocde);

	YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId(@Param("srecipeId")Integer srecipeId);
	
    int deleteByPrimaryKey(Integer uFoodErecipeId);

    int insert(YhFoodUserScrecipe record);

    int insertSelective(YhFoodUserScrecipe record);

    YhFoodUserScrecipe selectByPrimaryKey(Integer uFoodErecipeId);

    int updateByPrimaryKeySelective(YhFoodUserScrecipe record);

    int updateByPrimaryKeyWithBLOBs(YhFoodUserScrecipe record);

    int updateByPrimaryKey(YhFoodUserScrecipe record);
}