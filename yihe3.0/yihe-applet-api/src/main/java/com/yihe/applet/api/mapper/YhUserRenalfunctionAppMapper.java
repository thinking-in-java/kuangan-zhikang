package com.yihe.applet.api.mapper;
 
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserRenalfunctionApp;


public interface YhUserRenalfunctionAppMapper {

    List<YhUserRenalfunctionApp> findRenInfoAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

    List<YhUserRenalfunctionApp> findRenInfoByTimeType(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

    int insert(YhUserRenalfunctionApp record);
    int deleteByPrimaryKey(Integer renalid);

    int insertSelective(YhUserRenalfunctionApp record);


    YhUserRenalfunctionApp selectByPrimaryKey(Integer renalid);



    int updateByPrimaryKeySelective(YhUserRenalfunctionApp record);

    int updateByPrimaryKey(YhUserRenalfunctionApp record);
    
    List<YhUserRenalfunctionApp> selectByPrimaryKeyBYeid(@Param("eid") String eid);
    
    YhUserRenalfunctionApp selectByPrimaryKeyNewest(@Param("eid") String eid);
    
    List<YhUserRenalfunctionApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserRenalfunctionApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);

}