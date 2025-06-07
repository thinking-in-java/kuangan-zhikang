package com.yihe.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yihe.system.service.YhOrgTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.IntegralShoppingGrouping;
import com.yihe.system.mapper.IntegralShoppingGroupingMapper;
import com.yihe.system.service.IntegralShoppingGroupingService;

import javax.annotation.Resource;

@Service
public class IntegralShoppingGroupingServiceImpl implements IntegralShoppingGroupingService{
	@Autowired
	IntegralShoppingGroupingMapper IntegralShoppingGroupingMapper;
	@Resource
	YhOrgTreeService yhOrgTreeService;

	@Override
	public int deleteByPrimaryKey(Integer groupingId) {
		// TODO Auto-generated method stub
		return IntegralShoppingGroupingMapper.deleteByPrimaryKey(groupingId);
	}

	@Override
	public int updateByPrimaryKeySelective(IntegralShoppingGrouping record) {
		// TODO Auto-generated method stub
		return IntegralShoppingGroupingMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(IntegralShoppingGrouping record) {
		// TODO Auto-generated method stub
		return IntegralShoppingGroupingMapper.insertSelective(record);
	}

	@Override
	public PageInfo<IntegralShoppingGrouping> queryGroupingByCriteriaWithPage(String name,String orgCode,int page, int limit) {
		// TODO Auto-generated method stub
		//设置分页参数
        PageHelper.startPage(page,limit);
        List<IntegralShoppingGrouping> list = IntegralShoppingGroupingMapper.queryShoppingGroupingPage(name,orgCode);
		return new PageInfo<IntegralShoppingGrouping>(list);
	}

	@Override
	public IntegralShoppingGrouping selectGroupingName(String name) {
		// TODO Auto-generated method stub
		return IntegralShoppingGroupingMapper.selectGroupingName(name);
	}

	@Override
	public List<IntegralShoppingGrouping> selectAllGrouping(String orgCode) {
		// TODO Auto-generated method stub
		return IntegralShoppingGroupingMapper.selectAllGrouping(orgCode);
	}

	@Override
	public List<IntegralShoppingGrouping> selectGrouping(String name) {
		// TODO Auto-generated method stub
		return IntegralShoppingGroupingMapper.selectGrouping(name);
	}
}
