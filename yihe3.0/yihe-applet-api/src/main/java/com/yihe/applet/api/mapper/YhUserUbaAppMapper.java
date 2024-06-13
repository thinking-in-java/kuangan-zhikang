package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserUbaApp;

public interface YhUserUbaAppMapper {

    List<YhUserUbaApp>  findUserUbaAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

    List<YhUserUbaApp> findUbaInfoByTimeType(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);
	int insert(YhUserUbaApp record);
	
    int deleteByPrimaryKey(Integer ubaId);

    

    int insertSelective(YhUserUbaApp record);


    YhUserUbaApp selectByPrimaryKey(Integer ubaId);


    int updateByPrimaryKeySelective(YhUserUbaApp record);

    int updateByPrimaryKey(YhUserUbaApp record);
    
    List<YhUserUbaApp> selectByPrimaryKeyBYeid(String eid);
    
    YhUserUbaApp selectByPrimaryKeyNewest(String eid);
    

    List<YhUserUbaApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserUbaApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);
}