package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonagePhysical;
import com.yihe.system.mapper.YhUserPersonagePhysicalMapper;
import com.yihe.system.service.YhUserPersonagePhysicalService;
@Service
public class YhUserPersonagePhysicalServiceImpl implements YhUserPersonagePhysicalService{
	@Autowired
	YhUserPersonagePhysicalMapper YhUserPersonagePhysicalMapper;

	@Override
	public YhUserPersonagePhysical selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return YhUserPersonagePhysicalMapper.selectByPrimaryKey(userCode);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonagePhysical yhUserPersonagePhysical) {
		// TODO Auto-generated method stub
		return YhUserPersonagePhysicalMapper.updateByPrimaryKey(yhUserPersonagePhysical);
	}

	@Override
	public int insertSelective(YhUserPersonagePhysical yhUserPersonagePhysical) {
		// TODO Auto-generated method stub
		return YhUserPersonagePhysicalMapper.insertSelective(yhUserPersonagePhysical);
	}
	
}
