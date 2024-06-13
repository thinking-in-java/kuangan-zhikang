package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonageLive;
import com.yihe.system.mapper.YhUserPersonageLiveMapper;
import com.yihe.system.service.YhUserPersonageLiveService;
@Service
public class YhUserPersonageLiveServiceImpl implements YhUserPersonageLiveService{
	@Autowired
	YhUserPersonageLiveMapper yhUserPersonageLiveMapper;
	@Override
	public YhUserPersonageLive selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageLiveMapper.selectByPrimaryKey(userCode);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonageLive yhUserPersonageLive) {
		// TODO Auto-generated method stub
		return yhUserPersonageLiveMapper.updateByPrimaryKeySelective(yhUserPersonageLive);
	}

	@Override
	public int insertSelective(YhUserPersonageLive yhUserPersonageLive) {
		// TODO Auto-generated method stub
		return yhUserPersonageLiveMapper.insertSelective(yhUserPersonageLive);
	}


}
