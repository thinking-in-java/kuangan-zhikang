package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserBloodfatApp;
import org.apache.ibatis.annotations.Param;

/**
 * 血脂四项接口 Created by dyb on 2019/12/23.
 */
public interface YhUserBloodfatAppService {
	List<YhUserBloodfatApp> findBloodfatAllByUserCode(String usercode,Integer timetype,String strTime,String endTime);
	List<YhUserBloodfatApp> findBingTuInfo(String usercode,Integer timetype, String strTime,String endTime,Integer page);
	//查询体温某年数据根据usercode
	List<YhUserBloodfatApp> findTemperatureByYear(String time,String usercode,Integer page,Integer pagesize);
	//查询体温某月数据根据usercode
	List<YhUserBloodfatApp> findTemperatureByMonth(String month,String usercode,Integer page,Integer pagesize);
	//查询体温某周数据根据usercode
	List<YhUserBloodfatApp> findTemperatureByWeek(String strTime,String endTime,String usercode,Integer page,Integer pagesize);

	int deleteByPrimaryKey(Integer uBloodfatId);

	int insertSelective(YhUserBloodfatApp record);

	YhUserBloodfatApp selectByPrimaryKey(Integer uBloodfatId);

	int updateByPrimaryKeySelective(YhUserBloodfatApp record);

	List<YhUserBloodfatApp> selectByPrimaryKeyBYeid(String eid);

	YhUserBloodfatApp selectByPrimaryKeyNewest(String eid);

	List<YhUserBloodfatApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserBloodfatApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode);
}