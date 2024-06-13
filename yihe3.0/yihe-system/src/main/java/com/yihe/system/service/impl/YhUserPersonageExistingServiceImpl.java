package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonageExisting;
import com.yihe.system.mapper.YhUserPersonageExistingMapper;
import com.yihe.system.service.YhUserPersonageExistingService;
@Service
public class YhUserPersonageExistingServiceImpl implements YhUserPersonageExistingService{
	@Autowired
	YhUserPersonageExistingMapper yhUserPersonageExistingMapper;

	@Override
	public YhUserPersonageExisting selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageExistingMapper.selectByPrimaryKey(userCode);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonageExisting yhUserPersonageExisting) {
		// TODO Auto-generated method stub
		return yhUserPersonageExistingMapper.updateByPrimaryKey(yhUserPersonageExisting);
	}

	@Override
	public int insertSelective(YhUserPersonageExisting yhUserPersonageExisting) {
		// TODO Auto-generated method stub
		return yhUserPersonageExistingMapper.insertSelective(yhUserPersonageExisting);
	}
}
