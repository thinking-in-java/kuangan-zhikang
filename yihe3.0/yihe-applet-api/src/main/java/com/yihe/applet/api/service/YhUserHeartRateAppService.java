package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserHeartRateApp;
import com.yihe.applet.api.domain.YhUserTemperatureApp;


/**
 * YhUserHeartRateService接口 Created by dyb on 2020/3/6.
 */
public interface YhUserHeartRateAppService{

	List<YhUserHeartRateApp> findHeartRateAllByUserCode(String usercode,Integer timetype,String strTime,String endTime);

	//查询心率某年数据根据usercode
	List<YhUserHeartRateApp> findHeartRateByYear(String time, String usercode, Integer pageNum, Integer pageSize);
	//查询心率某月数据根据usercode
	List<YhUserHeartRateApp> findHeartRateByMonth(String month,String usercode,Integer pageNum,Integer pageSize);
	//查询心率某周数据根据usercode
	List<YhUserHeartRateApp> findHeartRateByWeek(String strTime,String endTime,String usercode,Integer pageNum,Integer pageSize);

	List<Integer> findHeartRateTimeFenBuYear(String time, String usercode, Integer page);
	List<Integer> findHeartRateTimeFenBuMonth(String month,String usercode,Integer page);
	List<Integer> findHeartRateTimeFenBuWeek(String strTime,String endTime,String usercode,Integer page);


	List<String> selectByPrimaryKeyFindEntryTime(String usercode);

	int findHeartRateGuoSu(String usercode,Integer timetype,String strTime,String endTime,Integer page);

	int findHeartRateGuoHuan(String usercode,Integer timetype,String strTime,String endTime,Integer page);

	int findHeartRateZhengChang(String usercode,Integer timetype,String strTime,String endTime,Integer page);
	
	List<Integer> findtemperatureTimeFenBu(String usercode);
	int updateByPrimaryKeySelective(YhUserHeartRateApp record);

	YhUserHeartRateApp selectByPrimaryKey(Integer rid);
	int deleteByPrimaryKey(Integer rid);
	int insertSelective(YhUserHeartRateApp record);
	List<YhUserHeartRateApp> findUserHeartRateByEidLimit7(String eid);

	List<YhUserHeartRateApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode);

	YhUserHeartRateApp selectByPrimaryKeyOrderTime(String E_User_Id);
}