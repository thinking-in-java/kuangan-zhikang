package com.yihe.applet.api.service;


import java.util.List;

import com.yihe.applet.api.domain.YhUserEcgApp;
 
/**
* YhUserEcgService接口
* Created by dyb on 2019/12/25.
*/
public interface YhUserEcgAppService {
	 int deleteByPrimaryKey(Integer uEcgId);

	    int insertSelective(YhUserEcgApp record);


	    YhUserEcgApp selectByPrimaryKey(Integer uEcgId);


	    int updateByPrimaryKeySelective(YhUserEcgApp record);
	YhUserEcgApp selectByPrimaryKeyOrderByTimeLimit1(String uEcgId);
	
	 List<YhUserEcgApp> selectByPrimaryKeyFindEntryTime(Integer E_User_Id);

	   	List<YhUserEcgApp> selectByPrimaryKeyFindEntryTimeAnduserid( String time,
	   		  Integer usercode);
}