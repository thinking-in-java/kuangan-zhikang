package com.yihe.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhFoodEnergyWeb;

public interface YhFoodEnergyWebMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhFoodEnergyWeb record);

    int insertSelective(YhFoodEnergyWeb record);

    YhFoodEnergyWeb selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhFoodEnergyWeb record);

    int updateByPrimaryKey(YhFoodEnergyWeb record);
    
    int findFoodEnergyBySupply(@Param("shape")Integer shape,@Param("activity")Integer activity);
}