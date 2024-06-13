package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserFoodWeeklyWeb;
import com.yihe.system.mapper.YhUserFoodWeeklyWebMapper;
import com.yihe.system.service.YhUserFoodWeeklyWebService;

@Service
public class YhUserFoodWeeklyWebServiceImpl implements YhUserFoodWeeklyWebService {
	@Autowired
	YhUserFoodWeeklyWebMapper YhUserFoodWeeklyWebMapper;

	@Override
	public PageInfo<YhUserFoodWeeklyWeb> findFoodWeekLyByUserCode(String usercode,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhUserFoodWeeklyWeb> list = YhUserFoodWeeklyWebMapper.findFoodWeekLyByUserCode(usercode);
		return new PageInfo<>(list);
	}

	@Override
	public YhUserFoodWeeklyWeb selectByPrimaryKey(Integer wid) {
		// TODO Auto-generated method stub
		return YhUserFoodWeeklyWebMapper.selectByPrimaryKey(wid);
	}

	
}
