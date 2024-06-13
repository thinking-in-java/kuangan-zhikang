package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserBloodsugerApp;
import org.apache.ibatis.annotations.Param;

/**
 * 血糖接口 Created by dyb on 2019/12/20.
 */
public interface YhUserBloodsugerAppService{

	List<YhUserBloodsugerApp> findBloodsugerAllByUserCode(String usercode,Integer timetype,String strTime,String endTime,String type);

	//查询体温某年数据根据usercode
	List<YhUserBloodsugerApp> findBloodsugerByYear(String type,String time, String usercode, Integer page,Integer pagesize);
	//查询体温某月数据根据usercode
	List<YhUserBloodsugerApp> findBloodsugerByMonth(String type,String month,String usercode,Integer page,Integer pagesize);
	//查询体温某周数据根据usercode
	List<YhUserBloodsugerApp> findBloodsugerByWeek(String type,String strTime,String endTime,String usercode,Integer page,Integer pagesize);

	List<Integer> findbloodsugerTimeFenBuYear(String time, String usercode, Integer page,String type);
	List<Integer> findbloodsugerTimeFenBuMonth(String month, String usercode, Integer page,String type);
	List<Integer> findbloodsugerTimeFenBuWeek(String strTime,String endTime,String usercode,Integer page,String type);



	List<Integer> findtemperatureTimeFenBu(String usercode);
	
	int insertSelective(YhUserBloodsugerApp record);

	
	int updateByPrimaryKeySelective(YhUserBloodsugerApp record);
	
	YhUserBloodsugerApp selectByPrimaryKey(Integer uBloodsugerId);

	List<YhUserBloodsugerApp> selectByPrimaryKeyByEidLimit7(String eid);

	List<YhUserBloodsugerApp> selectByPrimaryKeyBYeid(String usercode,String type);
	List<YhUserBloodsugerApp> selectByPrimaryKeyBYeid2(String usercode,Integer timetype,String strTime,String endTime,Integer page,String type);

	List<YhUserBloodsugerApp> selectByPrimaryKeyByMonthInfo(String eid, String shijian);

	YhUserBloodsugerApp selectByPrimaryKeyNewest(String eid);

	List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserBloodsugerApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode);
}