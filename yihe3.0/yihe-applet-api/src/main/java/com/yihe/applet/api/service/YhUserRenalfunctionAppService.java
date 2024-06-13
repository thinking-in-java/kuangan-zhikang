package com.yihe.applet.api.service;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserRenalfunctionApp;

 
/**
 * YhUserRenalfunctionService接口 Created by dyb on 2019/12/24.
 */
public interface YhUserRenalfunctionAppService {

	List<YhUserRenalfunctionApp> findRenInfoAllByUserCode(String usercode,Integer timetype,String strTime,String endTime);


	List<YhUserRenalfunctionApp> findRenInfoByTimeType(String usercode,Integer timetype,String strTime,String endTime);

	 int deleteByPrimaryKey(Integer renalid);

	    int insertSelective(YhUserRenalfunctionApp record);


	    YhUserRenalfunctionApp selectByPrimaryKey(Integer renalid);



	    int updateByPrimaryKeySelective(YhUserRenalfunctionApp record);

	    
	List<YhUserRenalfunctionApp> selectByPrimaryKeyBYeid(String eid);

	YhUserRenalfunctionApp selectByPrimaryKeyNewest(String eid);
	
	List<YhUserRenalfunctionApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserRenalfunctionApp> selectByPrimaryKeyFindEntryTimeAnduserid(  String time,
			String usercode);

}