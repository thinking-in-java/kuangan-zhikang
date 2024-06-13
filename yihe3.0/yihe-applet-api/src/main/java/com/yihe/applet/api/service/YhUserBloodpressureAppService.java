package com.yihe.applet.api.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserBloodpressureApp;
 
/**
 * 血压接口 Created by dyb on 2019/12/19.
 */
public interface YhUserBloodpressureAppService {
	List<YhUserBloodpressureApp> findUserBloodPressureAllInfoByTime(String usercode,Integer timetype,String strTime,String endTime,Integer page, String type);

	List<YhUserBloodpressureApp> findBloodpressureAllByUserCode(String usercode,Integer timetype,String strTime,String endTime,String type);

	List<Integer> findBloodPressureTimeFenBuYear(String time, String usercode,Integer page,String type);
	List<Integer> findBloodPressureTimeFenBuMonth(String month, String usercode,Integer page,String type);
	List<Integer> findBloodPressureTimeFenBuWeek(String strTime,String endTime,String usercode,Integer page,String type);


	//查询血压某年数据根据usercode
	List<YhUserBloodpressureApp> findBloodpressureByYear(String time, String usercode, Integer page, Integer pagesize,String type);
	//查询血压某月数据根据usercode
	List<YhUserBloodpressureApp> findBloodpressureByMonth(String month,String usercode,Integer page,Integer pagesize,String type);
	//查询血压某周数据根据usercode
	List<YhUserBloodpressureApp> findBloodpressureByWeek(String strTime,String endTime,String usercode,Integer page,Integer pagesize,String type);


	int	findUserBloodPressurePianDi(String usercode,Integer timetype,String strTime,String endTime,Integer page, String type);

	int	findUserBloodPressureZC(String usercode,Integer timetype,String strTime,String endTime,Integer page, String type);

    int	findUserBloodPressurepiangao(String usercode,Integer timetype,String strTime,String endTime,Integer page, String type);

    int	findUserBloodPressureqingdu(String usercode,Integer timetype,String strTime,String endTime,Integer page, String type);

    int	findUserBloodPressurezhongdu(String usercode,Integer timetype,String strTime,String endTime,Integer page, String type);

    int	findUserBloodPressurezhongdu2(String usercode,Integer timetype,String strTime,String endTime,Integer page, String type);

    int	findUserBloodPressuredanchun(String usercode,Integer timetype,String strTime,String endTime,Integer page, String type);
	
	List<Integer> findBloodPressureTimeFenBu(String usercode);
	
	List<YhUserBloodpressureApp> findBloodPressureByStrTimeAndEndTime(String usercode,String strTime, String endTime);

	
	int insertSelective(YhUserBloodpressureApp record);
	
	int updateByPrimaryKeySelective(YhUserBloodpressureApp record);
	
	YhUserBloodpressureApp selectByPrimaryKey(Integer uBloodpressureId);
	
	List<YhUserBloodpressureApp> selectByPrimaryKeyByEidLimit7(String eid);

	List<YhUserBloodpressureApp> selectByPrimaryKeyBYeid(String eid, String type);

	List<YhUserBloodpressureApp> selectByPrimaryKeyBYeidXinlv(String eid);

	List<YhUserBloodpressureApp> selectByPrimaryKeyByMonthInfo(String eid, String shijian);

	YhUserBloodpressureApp selectByPrimaryKeyNewest(String eid);

	List<String> selectByPrimaryKeyFindEntryTime(String eid);

	List<YhUserBloodpressureApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode);

	List<YhUserBloodpressureApp> selectByPrimaryKeyFindEntryTimeAnduseridXinlv(String time, String usercode);

	YhUserBloodpressureApp selectByPrimaryKeyNewestXinLV(String eid);
}