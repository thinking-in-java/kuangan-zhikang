package com.yihe.system.mapper;

import com.yihe.system.domain.YhFoodCookedWeb;

public interface YhFoodCookedWebMapper {
    int deleteByPrimaryKey(Integer fCookedid);

    int insert(YhFoodCookedWeb record);

    int insertSelective(YhFoodCookedWeb record);

    YhFoodCookedWeb selectByPrimaryKey(Integer fCookedid);

    int updateByPrimaryKeySelective(YhFoodCookedWeb record);

    int updateByPrimaryKey(YhFoodCookedWeb record);
}