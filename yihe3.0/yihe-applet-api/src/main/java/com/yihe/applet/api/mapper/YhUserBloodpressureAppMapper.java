package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserBloodpressureApp;
 

public interface YhUserBloodpressureAppMapper {
	List<YhUserBloodpressureApp> findUserBloodPressureAllInfoByTime(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page,@Param("type") String type);

	 List<YhUserBloodpressureApp> findBloodpressureAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("type") String type);

	int	findUserBloodPressurePianDi(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page,@Param("type") String type);

	int	findUserBloodPressureZC(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page,@Param("type") String type);

    int	findUserBloodPressurepiangao(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page,@Param("type") String type);

    int	findUserBloodPressureqingdu(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page,@Param("type") String type);

    int	findUserBloodPressurezhongdu(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page,@Param("type") String type);

    int	findUserBloodPressurezhongdu2(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page,@Param("type") String type);

    int	findUserBloodPressuredanchun(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page,@Param("type") String type);
	List<Integer> findBloodPressureTimeFenBu(@Param("usercode") String usercode);
	
	List<YhUserBloodpressureApp> findBloodPressureByStrTimeAndEndTime(@Param("usercode") String usercode,@Param("strTime") String strTime,@Param("endTime") String endTime);

	int deleteByPrimaryKey(Integer uBloodpressureId);

	int insert(YhUserBloodpressureApp record);

	int insertSelective(YhUserBloodpressureApp record);


	YhUserBloodpressureApp selectByPrimaryKey(Integer uBloodpressureId);
 

 
	int updateByPrimaryKeySelective(YhUserBloodpressureApp record);

	int updateByPrimaryKey(YhUserBloodpressureApp record);

	List<YhUserBloodpressureApp> selectByPrimaryKeyByEidLimit7(@Param("eid") String eid);

	List<YhUserBloodpressureApp> selectByPrimaryKeyBYeid(@Param("eid") String eid, @Param("type") String type);

	//查询血压某年数据根据usercode
	List<YhUserBloodpressureApp> findBloodpressureByYear(@Param("time")String time, @Param("usercode")String usercode, @Param("page")Integer page, @Param("pagesize")Integer pagesize,@Param("type")String type);
	//查询血压某月数据根据usercode
	List<YhUserBloodpressureApp> findBloodpressureByMonth(@Param("month")String month,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize,@Param("type")String type);
	//查询血压某周数据根据usercode
	List<YhUserBloodpressureApp> findBloodpressureByWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize,@Param("type")String type);

	List<Integer> findBloodPressureTimeFenBuYear(@Param("time")String time,@Param("usercode") String usercode,@Param("page") Integer page,@Param("type")String type);
	List<Integer> findBloodPressureTimeFenBuMonth(@Param("month")String month,@Param("usercode") String usercode,@Param("page") Integer page,@Param("type")String type);
	List<Integer> findBloodPressureTimeFenBuWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode") String usercode,@Param("page") Integer page,@Param("type")String type);


	List<YhUserBloodpressureApp> selectByPrimaryKeyBYeidXinlv(@Param("eid") String eid);

	List<YhUserBloodpressureApp> selectByPrimaryKeyByMonthInfo(@Param("eid") String eid, @Param("shijian") String shijian);

	YhUserBloodpressureApp selectByPrimaryKeyNewest(@Param("eid") String eid);

	YhUserBloodpressureApp selectByPrimaryKeyNewestXinLV(@Param("eid") String eid);

	List<String> selectByPrimaryKeyFindEntryTime(String eid);

	List<YhUserBloodpressureApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);

	List<YhUserBloodpressureApp> selectByPrimaryKeyFindEntryTimeAnduseridXinlv(@Param("time") String time,
			@Param("usercode") String usercode);

}