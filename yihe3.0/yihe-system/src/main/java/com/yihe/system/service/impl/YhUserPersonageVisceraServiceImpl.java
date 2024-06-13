package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonageViscera;
import com.yihe.system.mapper.YhUserPersonageVisceraMapper;
import com.yihe.system.service.YhUserPersonageVisceraService;
@Service
public class YhUserPersonageVisceraServiceImpl implements YhUserPersonageVisceraService{

	@Autowired
	YhUserPersonageVisceraMapper yhUserPersonageVisceraMapper;
	@Override
	public YhUserPersonageViscera selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageVisceraMapper.selectByPrimaryKey(userCode);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonageViscera yhUserPersonageViscera) {
		// TODO Auto-generated method stub
		return yhUserPersonageVisceraMapper.updateByPrimaryKey(yhUserPersonageViscera);
	}

	@Override
	public int insertSelective(YhUserPersonageViscera yhUserPersonageViscera) {
		// TODO Auto-generated method stub
		return yhUserPersonageVisceraMapper.insertSelective(yhUserPersonageViscera);
	}

}
