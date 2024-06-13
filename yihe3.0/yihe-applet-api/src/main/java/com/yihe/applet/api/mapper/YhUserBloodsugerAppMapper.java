package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserBloodsugerApp;
 
public interface YhUserBloodsugerAppMapper {

	List<YhUserBloodsugerApp> findBloodsugerAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("type") String type);

	//查询体温某年数据根据usercode
	List<YhUserBloodsugerApp> findBloodsugerByYear(@Param("type")String type,@Param("time")String time, @Param("usercode")String usercode, @Param("page")Integer page, @Param("pagesize")Integer pagesize);
	//查询体温某月数据根据usercode
	List<YhUserBloodsugerApp> findBloodsugerByMonth(@Param("type")String type,@Param("month")String month,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);
	//查询体温某周数据根据usercode
	List<YhUserBloodsugerApp> findBloodsugerByWeek(@Param("type")String type,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);

	List<Integer> findbloodsugerTimeFenBuYear(@Param("time")String time,@Param("usercode") String usercode,@Param("page") Integer page,@Param("type")String type);
	List<Integer> findbloodsugerTimeFenBuMonth(@Param("month")String month,@Param("usercode") String usercode,@Param("page") Integer page,@Param("type")String type);
	List<Integer> findbloodsugerTimeFenBuWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode") String usercode,@Param("page") Integer page,@Param("type")String type);



	List<Integer> findtemperatureTimeFenBu(String usercode);

	int deleteByPrimaryKey(Integer uBloodsugerId);

	int insert(YhUserBloodsugerApp record);

	int insertSelective(YhUserBloodsugerApp record);


	YhUserBloodsugerApp selectByPrimaryKey(Integer uBloodsugerId);



	int updateByPrimaryKeySelective(YhUserBloodsugerApp record);

	int updateByPrimaryKey(YhUserBloodsugerApp record);

	List<YhUserBloodsugerApp> selectByPrimaryKeyByEidLimit7(@Param("eid") String eid);

	List<YhUserBloodsugerApp> selectByPrimaryKeyBYeid(@Param("eid")String eid,@Param("type") String type);
	List<YhUserBloodsugerApp> selectByPrimaryKeyBYeid2(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page,@Param("type") String type);

	List<YhUserBloodsugerApp> selectByPrimaryKeyByMonthInfo(@Param("eid") String eid, @Param("shijian") String shijian);

	YhUserBloodsugerApp selectByPrimaryKeyNewest(@Param("eid") String eid);

	List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserBloodsugerApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);
}