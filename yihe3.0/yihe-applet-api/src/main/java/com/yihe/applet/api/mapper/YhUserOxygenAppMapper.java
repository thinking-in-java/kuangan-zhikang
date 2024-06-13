package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserOxygenApp;

public interface YhUserOxygenAppMapper {
	List<YhUserOxygenApp>  findOxygenAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

	//查询血氧某年数据根据usercode
	List<YhUserOxygenApp> findOxygenByYear(@Param("time")String time, @Param("usercode")String usercode, @Param("page")Integer page, @Param("pagesize")Integer pagesize);
	//查询血氧某月数据根据usercode
	List<YhUserOxygenApp> findOxygenByMonth(@Param("month")String month,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);
	//查询血氧某周数据根据usercode
	List<YhUserOxygenApp> findOxygenByWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);

	List<Integer> findOxygenTimeFenBuYear(@Param("time")String time,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findOxygenTimeFenBuMonth(@Param("month")String month,@Param("usercode") String usercode,@Param("page") Integer page);
	List<Integer> findOxygenTimeFenBuWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode") String usercode,@Param("page") Integer page);


	int findUserOxygenByZhengchang(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);
	
	int findUserOxygenByYichang(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);
	
	List<Integer> findtemperatureTimeFenBu(String usercode);
	
	List<YhUserOxygenApp> findUserOxyGenInfoByeidLimit7Order(@Param("eid") String eid);


	int deleteByPrimaryKey(Integer uOxygenId);

	int insert(YhUserOxygenApp record);

	int insertSelective(YhUserOxygenApp record);


	YhUserOxygenApp selectByPrimaryKey(Integer uOxygenId);

	YhUserOxygenApp selectByPrimaryKeyBytime(String E_User_Id);


	int updateByPrimaryKeySelective(YhUserOxygenApp record);

	int updateByPrimaryKey(YhUserOxygenApp record);

	List<YhUserOxygenApp> selectByPrimaryKeyAllInfo(Integer E_User_Id);

	List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserOxygenApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);
}