package com.yihe.system.service;

import com.yihe.system.domain.YhUserPermission;

/**
* YhUserPermissionService接口
* Created by lll on 2020/3/6.
*/

public interface YhUserPermissionService{
	int deleteuserrolepermissionbypid(Integer permissionId);
	int insert(YhUserPermission yhuserpermission);
	int updateByPrimaryKey(YhUserPermission yhuserpermission);
	int deletepermission(Integer permissionId);
	int deleteByPrimaryKey(Integer permissionId);
}