package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhFoodCooked;

public interface YhFoodCookedMapper {
    int deleteByPrimaryKey(Integer fCookedid);

    int insert(YhFoodCooked record);

    int insertSelective(YhFoodCooked record);

    YhFoodCooked selectByPrimaryKey(Integer fCookedid);

    int updateByPrimaryKeySelective(YhFoodCooked record);

    int updateByPrimaryKey(YhFoodCooked record);
}