package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserTemperatureApp;

public interface YhUserTemperatureAppMapper {

	List<YhUserTemperatureApp> findTiwenAllInfoByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

	//查询体温某年数据根据usercode
	List<YhUserTemperatureApp> findTemperatureByYear(@Param("time")String time,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);
	//查询体温某月数据根据usercode
	List<YhUserTemperatureApp> findTemperatureByMonth(@Param("month")String month,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);
	//查询体温某周数据根据usercode
	List<YhUserTemperatureApp> findTemperatureByWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);

	List<Integer> findtemperatureTimeFenBuYear(@Param("time")String time,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findtemperatureTimeFenBuMonth(@Param("month")String month,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findtemperatureTimeFenBuWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode") String usercode,@Param("page") Integer page);


	int findTemperatureByDiRe(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);

	int findTemperatureByGaoRe(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);

	int findTemperatureByZC(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);

	int findTemperatureByGD(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);


	List<YhUserTemperatureApp> findUserTemPerAtureByUsercode(@Param("usercode") String usercode);

	int deleteByPrimaryKey(Integer tid);

	int insert(YhUserTemperatureApp record);

	int insertSelective(YhUserTemperatureApp record);

	YhUserTemperatureApp selectByPrimaryKey(Integer tid);

	YhUserTemperatureApp selectByPrimaryKeyOrderTime(String E_User_Id);

	int updateByPrimaryKeySelective(YhUserTemperatureApp record);

	int updateByPrimaryKey(YhUserTemperatureApp record);

	List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserTemperatureApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);

}