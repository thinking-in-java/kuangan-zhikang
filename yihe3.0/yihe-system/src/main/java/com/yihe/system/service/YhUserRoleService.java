package com.yihe.system.service;

import com.yihe.system.domain.YhUserMakeCard;

import java.util.List;

/**
* YhUserRoleService接口
* Created by lll on 2020/3/6.
*/
public interface YhUserRoleService{
	Integer selectRoleId(String roleName);
	Integer selectcountbyname(String Name);
	YhUserMakeCard selectmakecard(Integer num);
	int updatemakecard(YhUserMakeCard cards);
	List<String> listimg();
}