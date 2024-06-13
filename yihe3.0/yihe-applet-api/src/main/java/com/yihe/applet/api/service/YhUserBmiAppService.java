package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserBmiApp;
import org.apache.ibatis.annotations.Param;

/**
 * YhUserBmiService接口 Created by dyb on 2019/12/19.
 */
public interface YhUserBmiAppService {

	List<YhUserBmiApp> findBmiInfoAllByUsercode(String usercode,Integer timetype,String strTime,String endTime);

	//查询体重某年数据根据usercode
	List<YhUserBmiApp> findWeightByYear(String time,String usercode, Integer page, Integer pagesize);
	//查询体重某月数据根据usercode
	List<YhUserBmiApp> findWeightByMonth(String month,String usercode,Integer page,Integer pagesize);
	//查询体重某周数据根据usercode
	List<YhUserBmiApp> findWeightByWeek(String strTime,String endTime,String usercode,Integer page,Integer pagesize);

	List<Integer> findWeightTimeFenBuYear(String time,String usercode,Integer page);
	List<Integer> findWeightTimeFenBuMonth(String month,String usercode,Integer page);
	List<Integer> findWeightTimeFenBuWeek(String strTime,String endTime,String usercode,Integer page);



	List<YhUserBmiApp> findUserBmiByUserCodeAll(String usercode);
	
	List<Integer> findtemperatureTimeFenBu(String usercode);
	
	int updateByExampleSelective(YhUserBmiApp record);
	
	int insertSelective(YhUserBmiApp record);
	int updateByPrimaryKeySelective(YhUserBmiApp record);
	YhUserBmiApp selectByPrimaryKey(Integer uBmiId);

	int updateByExampleSelective2(Integer uBmiId);

	List<YhUserBmiApp> selectByPrimaryKeyByUserIdLimit7(String eid);

	List<YhUserBmiApp> selectByPrimaryKeyByMonthInfo(String eid, String shijian);

	List<YhUserBmiApp> selectByPrimaryKeyBYeid(String usercode,Integer timetype,String strTime,String endTime,Integer page);

	YhUserBmiApp selectByPrimaryKeyNewest(String eid);

	List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserBmiApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode);
}