package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserOxygenApp;
import org.apache.ibatis.annotations.Param;


/**
 * YhUserOxygenService接口 Created by dyb on 2020/3/3.
 */
public interface YhUserOxygenAppService   {
	List<YhUserOxygenApp>  findOxygenAllByUserCode(String usercode,Integer timetype,String strTime,String endTime);
	//查询血氧某年数据根据usercode
	List<YhUserOxygenApp> findOxygenByYear(String time, String usercode, Integer page, Integer pagesize);
	//查询血氧某月数据根据usercode
	List<YhUserOxygenApp> findOxygenByMonth(String month,String usercode,Integer page,Integer pagesize);
	//查询血氧某周数据根据usercode
	List<YhUserOxygenApp> findOxygenByWeek(String strTime,String endTime,String usercode,Integer page,Integer pagesize);

	List<Integer> findOxygenTimeFenBuYear(String time, String usercode, Integer page);
	List<Integer> findOxygenTimeFenBuMonth(String month, String usercode,Integer page);
	List<Integer> findOxygenTimeFenBuWeek(String strTime,String endTime, String usercode,Integer page);


	int findUserOxygenByZhengchang(String usercode,Integer timetype,String strTime,String endTime,Integer page);
	
	int findUserOxygenByYichang(String usercode,Integer timetype,String strTime,String endTime,Integer page);
	List<Integer> findtemperatureTimeFenBu(String usercode);
	
	int deleteByPrimaryKey(Integer uOxygenId);
	YhUserOxygenApp selectByPrimaryKey(Integer uOxygenId);
	int insertSelective(YhUserOxygenApp record);
	int updateByPrimaryKeySelective(YhUserOxygenApp record);
	List<YhUserOxygenApp> findUserOxyGenInfoByeidLimit7Order(String eid);

	YhUserOxygenApp selectByPrimaryKeyBytime(String E_User_Id);

	List<YhUserOxygenApp> selectByPrimaryKeyAllInfo(Integer E_User_Id);

	List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserOxygenApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode);
}