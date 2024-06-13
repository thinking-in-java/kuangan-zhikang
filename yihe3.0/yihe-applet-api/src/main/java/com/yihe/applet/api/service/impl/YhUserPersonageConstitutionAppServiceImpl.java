package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonageConstitutionApp;
import com.yihe.applet.api.mapper.YhUserPersonageConstitutionAppMapper;
import com.yihe.applet.api.service.YhUserPersonageConstitutionAppService;

@Service
public class YhUserPersonageConstitutionAppServiceImpl implements YhUserPersonageConstitutionAppService{
	@Autowired
	YhUserPersonageConstitutionAppMapper  yhUserPersonageConstitutionAppMapper;

	@Override
	public int insertSelective(YhUserPersonageConstitutionApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageConstitutionAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonageConstitutionApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageConstitutionAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageConstitutionApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageConstitutionAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserPersonageConstitutionApp showconstitution(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageConstitutionAppMapper.showconstitution(userCode);
	}
}
