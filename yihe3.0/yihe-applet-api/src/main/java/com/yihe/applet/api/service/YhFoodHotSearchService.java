package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhFoodHotSearch;

public interface YhFoodHotSearchService {
	int hotFoodAddHeat(String foodName);
	
	YhFoodHotSearch findFoodHotSearchByFoodName(String foodName);
	
	List<YhFoodHotSearch> findFoodHotSearchInfoLimit10();

	int insertSelective(YhFoodHotSearch record);

	int updateByPrimaryKeySelective(YhFoodHotSearch record);
}
