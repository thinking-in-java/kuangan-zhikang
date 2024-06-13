package com.yihe.applet.api.mapper;

 
import java.util.List;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserBloodfatApp;

public interface YhUserBloodfatAppMapper {

	List<YhUserBloodfatApp> findBloodfatAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

	List<YhUserBloodfatApp> findBingTuInfo(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("page")Integer page);
	//查询体温某年数据根据usercode
	List<YhUserBloodfatApp> findTemperatureByYear(@Param("time")String time, @Param("usercode")String usercode, @Param("page")Integer page, @Param("pagesize")Integer pagesize);
	//查询体温某月数据根据usercode
	List<YhUserBloodfatApp> findTemperatureByMonth(@Param("month")String month,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);
	//查询体温某周数据根据usercode
	List<YhUserBloodfatApp> findTemperatureByWeek(@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("usercode")String usercode,@Param("page")Integer page,@Param("pagesize")Integer pagesize);

	int insert(YhUserBloodfatApp record);
	int deleteByPrimaryKey(Integer uBloodfatId);

	

	int insertSelective(YhUserBloodfatApp record);


	YhUserBloodfatApp selectByPrimaryKey(Integer uBloodfatId);
 
	int updateByPrimaryKeySelective(YhUserBloodfatApp record);

	int updateByPrimaryKey(YhUserBloodfatApp record);

	List<YhUserBloodfatApp> selectByPrimaryKeyBYeid(@Param("eid") String eid);

	YhUserBloodfatApp selectByPrimaryKeyNewest(@Param("eid") String eid);

	List<YhUserBloodfatApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserBloodfatApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);

}