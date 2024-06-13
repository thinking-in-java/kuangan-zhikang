package com.yihe.applet.api.service;


import java.util.List;

import com.yihe.applet.api.domain.YhUserOtherfunctionApp;
import org.apache.ibatis.annotations.Param;


/**
* YhUserOtherfunctionService接口
* Created by dyb on 2019/12/24.
*/
public interface YhUserOtherfunctionAppService  {
	List<YhUserOtherfunctionApp>findOtherAllByUserCode(String usercode,Integer timetype,String strTime,String endTime);

	List<YhUserOtherfunctionApp> findOherInfoByTimeType(String usercode,Integer timetype,String strTime, String endTime);


	int deleteByPrimaryKey(Integer otherid);
	    

	    int insertSelective(YhUserOtherfunctionApp record);


	    YhUserOtherfunctionApp selectByPrimaryKey(Integer otherid);


	    int updateByPrimaryKeySelective(YhUserOtherfunctionApp record);
	    
	List<YhUserOtherfunctionApp> selectByPrimaryKeyBYeid(String eid);
	
	YhUserOtherfunctionApp selectByPrimaryKeyNewest(String eid);
	
	 List<YhUserOtherfunctionApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

		List<YhUserOtherfunctionApp> selectByPrimaryKeyFindEntryTimeAnduserid(  String time,
				String usercode);
}