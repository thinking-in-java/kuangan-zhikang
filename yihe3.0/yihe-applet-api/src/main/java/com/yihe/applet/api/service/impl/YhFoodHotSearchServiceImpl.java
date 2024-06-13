package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhFoodHotSearch;
import com.yihe.applet.api.mapper.YhFoodHotSearchMapper;
import com.yihe.applet.api.service.YhFoodHotSearchService;
@Service
public class YhFoodHotSearchServiceImpl implements YhFoodHotSearchService {

	@Autowired
	YhFoodHotSearchMapper YhFoodHotSearchMapper;
	@Override
	public List<YhFoodHotSearch> findFoodHotSearchInfoLimit10() {
		// TODO Auto-generated method stub
		return YhFoodHotSearchMapper.findFoodHotSearchInfoLimit10();
	}

	@Override
	public int insertSelective(YhFoodHotSearch record) {
		// TODO Auto-generated method stub
		return YhFoodHotSearchMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhFoodHotSearch record) {
		// TODO Auto-generated method stub
		return YhFoodHotSearchMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhFoodHotSearch findFoodHotSearchByFoodName(String foodName) {
		// TODO Auto-generated method stub
		return YhFoodHotSearchMapper.findFoodHotSearchByFoodName(foodName);
	}

	@Override
	public int hotFoodAddHeat(String foodName) {
		// TODO Auto-generated method stub
		return YhFoodHotSearchMapper.hotFoodAddHeat(foodName);
	}

}
