package com.yihe.applet.api.mapper;
 
import java.util.List;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserBodyFatApp;

public interface YhUserBodyFatAppMapper {

	List<YhUserBodyFatApp> findBodyFatAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

	//查询体温某年数据根据usercode
	List<YhUserBodyFatApp> findBodyFatByYear(@Param("time")String time, @Param("usercode")String usercode, @Param("page")Integer page, @Param("pagesize")Integer pagesize);
	//查询体温某月数据根据usercode
	List<YhUserBodyFatApp> findBodyFatByMonth(@Param("month")String month,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);
	//查询体温某周数据根据usercode
	List<YhUserBodyFatApp> findBodyFatByWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);

	List<Integer> findBodyFatTimeFenBuYear(@Param("time")String time,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findBodyFatTimeFenBuMonth(@Param("month")String month,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findBodyFatTimeFenBuWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode") String usercode,@Param("page") Integer page);



	List<String> selectByPrimaryKeyFindEntryTime(String usercode);

	List<Integer> findtemperatureTimeFenBu(String usercode);

	int insert(YhUserBodyFatApp record);
	
	int deleteByPrimaryKey(Integer wubfId);

	int insertSelective(YhUserBodyFatApp record);

	int updateByPrimaryKeySelective(YhUserBodyFatApp record);

	YhUserBodyFatApp selectByPrimaryKey(Integer wubfId);



	

	int updateByPrimaryKey(YhUserBodyFatApp record);

	List<YhUserBodyFatApp> selectByPrimaryKeyByEidLimit7(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);

	List<YhUserBodyFatApp> selectByPrimaryKeyBYeid(@Param("eid") String eid);

	List<YhUserBodyFatApp> selectByPrimaryKeyByMonthInfo(@Param("eid") String eid, @Param("shijian") String shijian);

	YhUserBodyFatApp selectByPrimaryKeyNewest(@Param("eid") String eid);
	

	List<YhUserBodyFatApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);
}