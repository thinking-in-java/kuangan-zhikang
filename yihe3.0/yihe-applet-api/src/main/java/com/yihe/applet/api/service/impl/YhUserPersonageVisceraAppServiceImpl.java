package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonageVisceraApp;
import com.yihe.applet.api.mapper.YhUserPersonageVisceraAppMapper;
import com.yihe.applet.api.service.YhUserPersonageVisceraAppService;

@Service
public class YhUserPersonageVisceraAppServiceImpl implements YhUserPersonageVisceraAppService{
	@Autowired
	YhUserPersonageVisceraAppMapper yhUserPersonageVisceraAppMapper;

	@Override
	public int insertSelective(YhUserPersonageVisceraApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageVisceraAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonageVisceraApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageVisceraAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageVisceraApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageVisceraAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserPersonageVisceraApp showviscera(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageVisceraAppMapper.showviscera(userCode);
	}
}
