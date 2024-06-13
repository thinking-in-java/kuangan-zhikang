package com.yihe.applet.api.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserEcgApp;

public interface YhUserEcgAppMapper {

   

    int insert(YhUserEcgApp record);
    
    int deleteByPrimaryKey(Integer uEcgId);

    int insertSelective(YhUserEcgApp record);


    YhUserEcgApp selectByPrimaryKey(Integer uEcgId);


    int updateByPrimaryKeySelective(YhUserEcgApp record);

    int updateByPrimaryKey(YhUserEcgApp record);
    
    YhUserEcgApp selectByPrimaryKeyOrderByTimeLimit1(String eid);
    
    List<YhUserEcgApp> selectByPrimaryKeyFindEntryTime(Integer E_User_Id);

   	List<YhUserEcgApp> selectByPrimaryKeyFindEntryTimeAnduserid(@Param("time") String time,
   			@Param("usercode") Integer usercode);
    
    
}