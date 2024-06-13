package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonageConstitution;
import com.yihe.system.mapper.YhUserPersonageConstitutionMapper;
import com.yihe.system.service.YhUserPersonageConstitutionService;
@Service
public class YhUserPersonageConstitutionServiceImpl implements YhUserPersonageConstitutionService{

	@Autowired
	YhUserPersonageConstitutionMapper  yhUserPersonageConstitutionMapper;
	@Override
	public YhUserPersonageConstitution selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageConstitutionMapper.selectByPrimaryKey(userCode);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonageConstitution yhUserPersonageConstitution) {
		// TODO Auto-generated method stub
		return yhUserPersonageConstitutionMapper.updateByPrimaryKey(yhUserPersonageConstitution);
	}

	@Override
	public int insertSelective(YhUserPersonageConstitution yhUserPersonageConstitution) {
		// TODO Auto-generated method stub
		return yhUserPersonageConstitutionMapper.insertSelective(yhUserPersonageConstitution);
	}

}
