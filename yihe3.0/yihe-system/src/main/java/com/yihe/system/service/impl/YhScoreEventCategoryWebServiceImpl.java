package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhScoreEventCategoryWeb;
import com.yihe.system.mapper.YhScoreEventCategoryWebMapper;
import com.yihe.system.service.YhScoreEventCategoryWebService;
@Service
public class YhScoreEventCategoryWebServiceImpl implements YhScoreEventCategoryWebService{

	@Autowired
	YhScoreEventCategoryWebMapper yhScoreEventCategoryWebMapper;
	
	@Override
	public PageInfo<YhScoreEventCategoryWeb> queryUserByCriteriaWithPage(Integer type,Integer putaway,String eventName,int page, int limit) {
		// TODO Auto-generated method stub
		 PageHelper.startPage(page,limit);
		 List<YhScoreEventCategoryWeb> list = yhScoreEventCategoryWebMapper.selectYhScoreEventCategoryPage(type,putaway,eventName);
		 return new PageInfo<YhScoreEventCategoryWeb>(list);
	}

	@Override
	public int updateByPrimaryKeySelective(YhScoreEventCategoryWeb record) {
		// TODO Auto-generated method stub
		return yhScoreEventCategoryWebMapper.updateByPrimaryKeySelective(record);
	}

}
