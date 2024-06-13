package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserOtherfunctionApp;

public interface YhUserOtherfunctionAppMapper {

  List<YhUserOtherfunctionApp>findOtherAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

 List<YhUserOtherfunctionApp> findOherInfoByTimeType(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

    int insert(YhUserOtherfunctionApp record);
    int deleteByPrimaryKey(Integer otherid);
    

    int insertSelective(YhUserOtherfunctionApp record);


    YhUserOtherfunctionApp selectByPrimaryKey(Integer otherid);


    int updateByPrimaryKeySelective(YhUserOtherfunctionApp record);

    int updateByPrimaryKey(YhUserOtherfunctionApp record);
    
    List<YhUserOtherfunctionApp> selectByPrimaryKeyBYeid(@Param("eid")String eid);
    
    YhUserOtherfunctionApp selectByPrimaryKeyNewest(@Param("eid")String eid);
    

    List<YhUserOtherfunctionApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserOtherfunctionApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);
}