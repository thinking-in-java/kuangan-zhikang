package com.yihe.system.service;

import com.yihe.system.domain.YhUserRole;

import java.util.List;
import java.util.Map;

public interface YhRoleManageService {
	List<YhUserRole> selectallrole();
	List<YhUserRole> selectrolebycode(Integer roletype,String usercode);
	List<YhUserRole> selecdoctorrole(Integer roletype,String usercode);
	int selectuserrolepowerbyroleid(int roleId);
	Map yhuserpermission(int roleId);
	Map selectdoctorrolepower(int roleId);
	int changerolepowerbyroleid(String authIds,int roleId);
	int deleterolepermission(int roleId);
	int insert(YhUserRole yhuerrole);
	int maxorder();
	int updateByPrimaryKey(YhUserRole yhuerrole);
	int selectrolenamecount(YhUserRole yhuerrole);
	int deleteByPrimaryKey(int roleId);
	int selectpidbyusercode(String usercode);
	
	List<YhUserRole> selectrolebycode1(Integer roletype,String usercode);
}
