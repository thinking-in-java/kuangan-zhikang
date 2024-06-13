package com.yihe.applet.api.mapper;

 
import java.util.List;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserBmiApp;

public interface YhUserBmiAppMapper {

	List<YhUserBmiApp> findBmiInfoAllByUsercode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

	//查询体重某年数据根据usercode
	List<YhUserBmiApp> findWeightByYear(@Param("time")String time, @Param("usercode")String usercode, @Param("page")Integer page, @Param("pagesize")Integer pagesize);
	//查询体重某月数据根据usercode
	List<YhUserBmiApp> findWeightByMonth(@Param("month")String month,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);
	//查询体重某周数据根据usercode
	List<YhUserBmiApp> findWeightByWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);

	List<Integer> findWeightTimeFenBuYear(@Param("time")String time,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findWeightTimeFenBuMonth(@Param("month")String month,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findWeightTimeFenBuWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode") String usercode,@Param("page") Integer page);




	List<YhUserBmiApp> findUserBmiByUserCodeAll(String usercode);
	
	List<Integer> findtemperatureTimeFenBu(String usercode);

	int deleteByPrimaryKey(Integer uBmiId);

	int insert(YhUserBmiApp record);

	int insertSelective(YhUserBmiApp record);


	YhUserBmiApp selectByPrimaryKey(Integer uBmiId);
	
	int updateByExampleSelective2(Integer uBmiId);
	
	int updateByExampleSelective(@Param("record")YhUserBmiApp record);
	 
	int updateByPrimaryKeySelective(YhUserBmiApp record);

	int updateByPrimaryKey(YhUserBmiApp record);

	List<YhUserBmiApp> selectByPrimaryKeyByUserIdLimit7(@Param("eid") String eid);

	List<YhUserBmiApp> selectByPrimaryKeyByMonthInfo(@Param("eid") String eid, @Param("shijian") String shijian);

	List<YhUserBmiApp> selectByPrimaryKeyBYeid(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);

	YhUserBmiApp selectByPrimaryKeyNewest(@Param("eid") String eid);

	List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserBmiApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);
}