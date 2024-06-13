package com.yihe.applet.api.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserTemperatureApp;

/**
 * YhUserTemperatureService接口 Created by dyb on 2020/3/3.
 */
public interface YhUserTemperatureAppService {

	List<YhUserTemperatureApp> findTiwenAllInfoByUserCode(String usercode,Integer timetype,String strTime,String endTime);

	//查询体温某年数据根据usercode
	List<YhUserTemperatureApp> findTemperatureByYear(String time, String usercode,Integer pageNum,Integer pageSize);
	//查询体温某月数据根据usercode
	List<YhUserTemperatureApp> findTemperatureByMonth(String month,String usercode,Integer pageNum,Integer pageSize);
	//查询体温某周数据根据usercode
	List<YhUserTemperatureApp> findTemperatureByWeek(String strTime,String endTime,String usercode,Integer pageNum,Integer pageSize);

	List<Integer> findtemperatureTimeFenBuYear(String time, String usercode, Integer page);
	List<Integer> findtemperatureTimeFenBuMonth(String month,String usercode,Integer page);
	List<Integer> findtemperatureTimeFenBuWeek(String strTime,String endTime,String usercode,Integer page);


	int findTemperatureByDiRe(String usercode,Integer timetype,String strTime,String endTime,Integer page);

	int findTemperatureByGaoRe(String usercode,Integer timetype,String strTime,String endTime,Integer page);

	int findTemperatureByZC(String usercode,Integer timetype,String strTime,String endTime,Integer page);

	int findTemperatureByGD(String usercode,Integer timetype,String strTime,String endTime,Integer page);



	YhUserTemperatureApp selectByPrimaryKey(Integer tid);

	
	int deleteByPrimaryKey(Integer tid);

	int insertSelective(YhUserTemperatureApp record);

	int updateByPrimaryKeySelective(YhUserTemperatureApp record);

	List<YhUserTemperatureApp> findUserTemPerAtureByUsercode(String usercode);

	YhUserTemperatureApp selectByPrimaryKeyOrderTime(String E_User_Id);

	List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserTemperatureApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode);
}