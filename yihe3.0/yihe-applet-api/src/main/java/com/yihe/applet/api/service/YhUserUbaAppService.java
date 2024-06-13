package com.yihe.applet.api.service;


import java.util.List;

import com.yihe.applet.api.domain.YhUserUbaApp;
import org.apache.ibatis.annotations.Param;


/**
 * YhUserUbaService接口 Created by dyb on 2019/12/25.
 */
public interface YhUserUbaAppService  {

	List<YhUserUbaApp>  findUserUbaAllByUserCode(String usercode,Integer timetype,String strTime,String endTime);

	List<YhUserUbaApp> findUbaInfoByTimeType(String usercode, Integer timetype,String strTime,String endTime);

	int deleteByPrimaryKey(Integer ubaId);

	    

	    int insertSelective(YhUserUbaApp record);


	    YhUserUbaApp selectByPrimaryKey(Integer ubaId);


	    int updateByPrimaryKeySelective(YhUserUbaApp record);

	List<YhUserUbaApp> selectByPrimaryKeyBYeid(String eid);

	YhUserUbaApp selectByPrimaryKeyNewest(String eid);
	
	  List<YhUserUbaApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

		List<YhUserUbaApp> selectByPrimaryKeyFindEntryTimeAnduserid( String time,
				String usercode);
}