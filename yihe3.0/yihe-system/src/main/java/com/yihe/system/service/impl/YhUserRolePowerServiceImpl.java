package com.yihe.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserRolePower;
import com.yihe.system.mapper.YhUserRolePowerMapper;
import com.yihe.system.service.YhUserRolePowerService;

/**
* YhUserRolePowerService实现
* Created by lll on 2020/3/6.
*/
@Service
@Transactional

public class YhUserRolePowerServiceImpl  implements YhUserRolePowerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserRolePowerServiceImpl.class);

    @Autowired
    YhUserRolePowerMapper yhUserRolePowerMapper;
 
	@Override
	public int inssert(YhUserRolePower record) {
		// TODO Auto-generated method stub
		return yhUserRolePowerMapper.insert(record);
	}

	@Override
	public YhUserRolePower selectByPowerId(String userid) {
		// TODO Auto-generated method stub
		return yhUserRolePowerMapper.selectByPowerId(userid);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserRolePower record) {
		// TODO Auto-generated method stub
		return yhUserRolePowerMapper.updateByPrimaryKeySelective(record);
	}

}