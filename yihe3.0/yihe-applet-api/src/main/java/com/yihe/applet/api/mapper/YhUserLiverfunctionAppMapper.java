package com.yihe.applet.api.mapper;
 
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserLiverfunctionApp;

public interface YhUserLiverfunctionAppMapper {

    List<YhUserLiverfunctionApp> findLiverAllByUserCode(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

    List<YhUserLiverfunctionApp> findLiverByTimeType(@Param("usercode")String usercode,@Param("timetype")Integer timetype,@Param("strTime")String strTime,@Param("endTime")String endTime);

	  int insert(YhUserLiverfunctionApp record);
    int deleteByPrimaryKey(Integer liverid);

  

    int insertSelective(YhUserLiverfunctionApp record);


    YhUserLiverfunctionApp selectByPrimaryKey(Integer liverid);



    int updateByPrimaryKeySelective(YhUserLiverfunctionApp record);

    int updateByPrimaryKey(YhUserLiverfunctionApp record);
    
    List<YhUserLiverfunctionApp> selectByPrimaryKeyBYeid(@Param("eid")String eid);
    
    YhUserLiverfunctionApp selectByPrimaryKeyNewest(@Param("eid")String eid);
    
    List<YhUserLiverfunctionApp> selectByPrimaryKeyFindEntryTime(String E_User_Id);

	List<YhUserLiverfunctionApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
			@Param("usercode") String usercode);

}