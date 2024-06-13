package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonageLiveApp;
import com.yihe.applet.api.mapper.YhUserPersonageLiveAppMapper;
import com.yihe.applet.api.service.YhUserPersonageLiveAppService;

@Service
public class YhUserPersonageLiveAppServiceImpl implements YhUserPersonageLiveAppService{
	@Autowired
	YhUserPersonageLiveAppMapper yhUserPersonageLiveAppMapper;

	@Override
	public int insertSelective(YhUserPersonageLiveApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageLiveAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonageLiveApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageLiveAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageLiveApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageLiveAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserPersonageLiveApp showlive(String usercode) {
		// TODO Auto-generated method stub
		return yhUserPersonageLiveAppMapper.showlive(usercode);
	}
}
