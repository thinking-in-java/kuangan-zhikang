package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonagePhysicalApp;
import com.yihe.applet.api.mapper.YhUserPersonagePhysicalAppMapper;
import com.yihe.applet.api.service.YhUserPersonagePhysicalAppService;

@Service
public class YhUserPersonagePhysicalAppServiceImpl implements YhUserPersonagePhysicalAppService{
	@Autowired
	YhUserPersonagePhysicalAppMapper yhUserPersonagePhysicalAppMapper;

	@Override
	public int insertSelective(YhUserPersonagePhysicalApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonagePhysicalAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonagePhysicalApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonagePhysicalAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonagePhysicalApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonagePhysicalAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserPersonagePhysicalApp showphysical(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonagePhysicalAppMapper.showphysical(userCode);
	}
}
