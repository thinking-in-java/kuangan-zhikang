package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhUserReginfo;


public interface YhUserManageService{
	List<YhUserReginfo> selectuseranrole(YhUserReginfo regin);
	int deleterolepowerbyusercode(String userCode);
	int deleteuserbyid(String userCode);
	int updateuserbyid(YhUserReginfo yhuserreginfo);
	int selectcount();
}
