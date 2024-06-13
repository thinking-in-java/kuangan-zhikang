package com.yihe.system.service.impl;

import com.yihe.system.domain.YhUserMakeCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.mapper.YhUserRoleMapper;
import com.yihe.system.service.YhUserRoleService;

import java.util.List;

/**
* YhUserRoleService实现
* Created by lll on 2020/3/6.
*/
@Service
@Transactional

public class YhUserRoleServiceImpl implements YhUserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserRoleServiceImpl.class);

    @Autowired
    YhUserRoleMapper yhUserRoleMapper; 

	@Override
	public Integer selectRoleId(String roleName) {
		// TODO Auto-generated method stub
		return yhUserRoleMapper.selectRoleId(roleName);
	}

	@Override
	public Integer selectcountbyname(String Name) {
		return yhUserRoleMapper.selectcountbyname(Name);
	}

	@Override
	public YhUserMakeCard selectmakecard(Integer num) {
		return yhUserRoleMapper.selectmakecard(num);
	}

	@Override
	public int updatemakecard(YhUserMakeCard cards) {
		return yhUserRoleMapper.updatemakecard(cards);
	}

	@Override
	public List<String> listimg() {
		return yhUserRoleMapper.listimg();
	}

}