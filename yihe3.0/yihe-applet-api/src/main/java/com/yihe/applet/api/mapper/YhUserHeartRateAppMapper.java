package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserHeartRateApp;

public interface YhUserHeartRateAppMapper {

	List<YhUserHeartRateApp> findHeartRateAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

	//查询心率某年数据根据usercode
	List<YhUserHeartRateApp> findHeartRateByYear(@Param("time")String time, @Param("usercode")String usercode, @Param("page")Integer page, @Param("pagesize")Integer pagesize);
	//查询心率某月数据根据usercode
	List<YhUserHeartRateApp> findHeartRateByMonth(@Param("month")String month,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);
	//查询心率某周数据根据usercode
	List<YhUserHeartRateApp> findHeartRateByWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);

	List<Integer> findHeartRateTimeFenBuYear(@Param("time")String time,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findHeartRateTimeFenBuMonth(@Param("month")String month,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findHeartRateTimeFenBuWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode") String usercode,@Param("page") Integer page);



	List<String> selectByPrimaryKeyFindEntryTime(String usercode);

	int findHeartRateGuoSu(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);

	int findHeartRateGuoHuan(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);

	int findHeartRateZhengChang(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);
	
	List<Integer> findtemperatureTimeFenBu(String usercode);
	
	List<YhUserHeartRateApp> findUserHeartRateByEidLimit7(@Param("eid") String eid);


	int deleteByPrimaryKey(Integer rid);

	int insert(YhUserHeartRateApp record);

	int insertSelective(YhUserHeartRateApp record);


	YhUserHeartRateApp selectByPrimaryKey(Integer rid);



	int updateByPrimaryKeySelective(YhUserHeartRateApp record);

	int updateByPrimaryKey(YhUserHeartRateApp record);

	YhUserHeartRateApp selectByPrimaryKeyOrderTime(String E_User_Id);

	List<YhUserHeartRateApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);
}