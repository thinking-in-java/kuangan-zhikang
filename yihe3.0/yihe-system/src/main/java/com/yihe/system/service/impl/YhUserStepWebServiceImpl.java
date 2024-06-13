package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserStepWeb;
import com.yihe.system.mapper.YhUserStepWebMapper;
import com.yihe.system.service.YhUserStepWebService;

@Service
@Transactional
public class YhUserStepWebServiceImpl implements YhUserStepWebService{
	@Autowired
	YhUserStepWebMapper  yhUserStepWebMapper;  

	@Override
	public int deleteByPrimaryKey(Integer stepId) {
		// TODO Auto-generated method stub
		return yhUserStepWebMapper.deleteByPrimaryKey(stepId);
	}

	@Override
	public int insertSelective(YhUserStepWeb record) {
		// TODO Auto-generated method stub
		return yhUserStepWebMapper.insertSelective(record);
	}
 
	@Override
	public YhUserStepWeb selectByPrimaryKey(Integer stepId) {
		// TODO Auto-generated method stub
		return yhUserStepWebMapper.selectByPrimaryKey(stepId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserStepWeb record) {
		// TODO Auto-generated method stub
		return yhUserStepWebMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserStepWeb selectUserIntegral(String userCode) {
		// TODO Auto-generated method stub
		return yhUserStepWebMapper.selectUserIntegral(userCode);
	}
}
