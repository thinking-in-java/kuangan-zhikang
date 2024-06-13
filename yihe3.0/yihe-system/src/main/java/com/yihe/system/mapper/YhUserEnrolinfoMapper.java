package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserEnrolinfoExample;

public interface YhUserEnrolinfoMapper {

    String findUserEnergyUptimeInfo(String usercode);
	List<YhUserEnrolinfo> findUserEnrolinfoByDoctorInfo(@Param("doctor")String doctor,@Param("ginfo")String ginfo);
	
    long countByExample(YhUserEnrolinfoExample example);

    int deleteByExample(YhUserEnrolinfoExample example);

    int deleteByPrimaryKey(Integer eUserId);

    int insert(YhUserEnrolinfo record);

    int insertSelective(YhUserEnrolinfo record);

    List<YhUserEnrolinfo> selectByExample(YhUserEnrolinfoExample example);

    YhUserEnrolinfo selectByPrimaryKey(Integer eUserId);
    
    int updateByExampleSelectiveByusercode(Map<String, String> map);

    int updateByExampleSelective(@Param("record") YhUserEnrolinfo record, @Param("example") YhUserEnrolinfoExample example);
    
    int updateByExampleByusercodeForEval(@Param("record") YhUserEnrolinfo record);

    int updateByExample(@Param("record") YhUserEnrolinfo record, @Param("example") YhUserEnrolinfoExample example);

    int updateByPrimaryKeySelective(YhUserEnrolinfo record);

    int updateByPrimaryKey(YhUserEnrolinfo record);
    
    YhUserEnrolinfo selectByPrimaryKeyByUsercode(String usercode);
    
    int modifyInfoByEva(YhUserEnrolinfo record);
}