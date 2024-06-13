package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonageExistingApp;
import com.yihe.applet.api.mapper.YhUserPersonageExistingAppMapper;
import com.yihe.applet.api.service.YhUserPersonageExistingAppService;
@Service
public class YhUserPersonageExistingAppServiceImpl implements YhUserPersonageExistingAppService{
		@Autowired
		YhUserPersonageExistingAppMapper yhUserPersonageExistingAppMapper;
	@Override
	public int insertSelective(YhUserPersonageExistingApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageExistingAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonageExistingApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageExistingAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageExistingApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageExistingAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserPersonageExistingApp showexisting(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageExistingAppMapper.showexisting(userCode);
	}

}
