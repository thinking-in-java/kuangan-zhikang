package com.yihe.applet.api.service;


import java.util.List;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserBodyFatApp;
 

/**
 * 体脂接口 Created by dyb on 2019/12/23.
 */
public interface YhUserBodyFatAppService {

	List<YhUserBodyFatApp> findBodyFatAllByUserCode(String usercode,Integer timetype,String strTime,String endTime);

	//查询体温某年数据根据usercode
	List<YhUserBodyFatApp> findBodyFatByYear(String time, String usercode, Integer pageNum, Integer pageSize);
	//查询体温某月数据根据usercode
	List<YhUserBodyFatApp> findBodyFatByMonth(String month,String usercode,Integer pageNum,Integer pageSize);
	//查询体温某周数据根据usercode
	List<YhUserBodyFatApp> findBodyFatByWeek(String strTime,String endTime,String usercode,Integer pageNum,Integer pageSize);

	List<Integer> findBodyFatTimeFenBuYear(String time, String usercode, Integer page);
	List<Integer> findBodyFatTimeFenBuMonth(String month,String usercode,Integer page);
	List<Integer> findBodyFatTimeFenBuWeek(String strTime,String endTime,String usercode,Integer page);

	List<String> selectByPrimaryKeyFindEntryTime(String usercode);
	List<Integer> findtemperatureTimeFenBu(String usercode);
	
	int deleteByPrimaryKey(Integer wubfId);

	int insertSelective(YhUserBodyFatApp record);

	int updateByPrimaryKeySelective(YhUserBodyFatApp record);

	YhUserBodyFatApp selectByPrimaryKey(Integer wubfId);
	
	List<YhUserBodyFatApp> selectByPrimaryKeyByEidLimit7(String usercode,Integer timetype,String strTime,String endTime,Integer page);

	List<YhUserBodyFatApp> selectByPrimaryKeyBYeid(String eid);

	List<YhUserBodyFatApp> selectByPrimaryKeyByMonthInfo(String eid, String shijian);
	
	YhUserBodyFatApp selectByPrimaryKeyNewest(String eid);
	
	List<YhUserBodyFatApp> selectByPrimaryKeyFindEntryTimeAnduserid(  String time,
			String usercode);
}