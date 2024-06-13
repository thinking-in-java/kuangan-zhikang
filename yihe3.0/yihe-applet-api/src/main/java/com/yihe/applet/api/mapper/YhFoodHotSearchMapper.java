package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhFoodHotSearch;

public interface YhFoodHotSearchMapper {
	int hotFoodAddHeat(String foodName);
	
	YhFoodHotSearch findFoodHotSearchByFoodName(String foodName);
	
	List<YhFoodHotSearch> findFoodHotSearchInfoLimit10();
	
    int deleteByPrimaryKey(Integer hid);

    int insert(YhFoodHotSearch record);

    int insertSelective(YhFoodHotSearch record);

    YhFoodHotSearch selectByPrimaryKey(Integer hid);

    int updateByPrimaryKeySelective(YhFoodHotSearch record);

    int updateByPrimaryKey(YhFoodHotSearch record);
}