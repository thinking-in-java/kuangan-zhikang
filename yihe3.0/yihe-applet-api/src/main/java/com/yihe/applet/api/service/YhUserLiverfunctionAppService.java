package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserLiverfunctionApp;
import org.apache.ibatis.annotations.Param;

/**
 * YhUserLiverfunctionService接口 Created by dyb on 2019/12/24.
 */
public interface YhUserLiverfunctionAppService {

	List<YhUserLiverfunctionApp> findLiverAllByUserCode(String usercode,Integer timetype,String strTime,String endTime);

	List<YhUserLiverfunctionApp> findLiverByTimeType(String usercode, Integer timetype, String strTime, String endTime);


	int deleteByPrimaryKey(Integer liverid);

	int insertSelective(YhUserLiverfunctionApp record);

	YhUserLiverfunctionApp selectByPrimaryKey(Integer liverid);

	int updateByPrimaryKeySelective(YhUserLiverfunctionApp record);

	List<YhUserLiverfunctionApp> selectByPrimaryKeyBYeid(String eid);

	YhUserLiverfunctionApp selectByPrimaryKeyNewest(String eid);

	List<YhUserLiverfunctionApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserLiverfunctionApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode);
}