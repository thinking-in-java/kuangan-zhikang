package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhUserPermission;
import com.yihe.system.domain.YhUserRole;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年3月6日 下午3:01:10 
 *@version 1.0 
 */
public interface  YhUserApiMapper {
	    // 根据用户id获取所拥有的权限
		List<YhUserPermission> selectYhUserPermissionByUserId(String userId);
		// 根据用户id获取所属的角色
		List<YhUserRole> selectYhUserRoleByUserId(String userId);

}
