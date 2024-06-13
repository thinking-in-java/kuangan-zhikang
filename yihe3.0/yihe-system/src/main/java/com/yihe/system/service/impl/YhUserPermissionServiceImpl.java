package com.yihe.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.mapper.YhUserPermissionMapper;
import com.yihe.system.service.YhUserPermissionService;

/**
* YhUserPermissionService实现
* Created by lll on 2020/3/6.
*/
@Service
@Transactional

public class YhUserPermissionServiceImpl  implements YhUserPermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPermissionServiceImpl.class);

    @Autowired
    YhUserPermissionMapper yhUserPermissionMapper;

	@Override
	public int deleteuserrolepermissionbypid(Integer permissionId) {
		// TODO Auto-generated method stub
		return yhUserPermissionMapper.deleteuserrolepermissionbypid(permissionId);
	}

	@Override
	public int insert(YhUserPermission yhuserpermission) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(YhUserPermission yhuserpermission) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletepermission(Integer permissionId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer permissionId) {
		// TODO Auto-generated method stub
		return 0;
	}

}