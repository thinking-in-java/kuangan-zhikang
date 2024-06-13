package com.yihe.applet.api.service;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserReginfoApp;


/**
* YhUserReginfoService接口
* Created by dyb on 2020/2/25.
*/
public interface YhUserReginfoAppService  {
	Integer findUserRole(String usercode);
	int findUserCodeByOrgIsExist(String userCode);
	List<String> findUserCodeByUserTypeFor5();
	int findUserNameExist(String username);
	YhUserReginfoApp selectByUserNameAndUserPwd( String userName,String userPwd);
	YhUserReginfoApp selectByshoujiAndUsername(String userName, String mobile);
	int updateByUsernameAndMobile(String userName, String userPwd,String mobile);
	int updateByUsercodeAndLoginTime(YhUserReginfoApp record);
	YhUserReginfoApp findYhUserReginfo(String userCode);
	 int updatePwd(YhUserReginfoApp yhUserReginfo);
	 YhUserReginfoApp selectUserPwd( String userCode,String userPwd);
	 int insertSelective(YhUserReginfoApp record);
	  int updateByPrimaryKeySelective(YhUserReginfoApp record);
	  YhUserReginfoApp finddoctorHderer(String userCode);
	Map<String,Integer> findUserInfo(String userCode);
}