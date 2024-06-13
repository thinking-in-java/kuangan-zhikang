package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserEnrolinfoExample;

/**
 * YhUserEnrolinfoService接口 Created by dyb on 2020/3/19.
 */
public interface YhUserEnrolinfoService {
	List<YhUserEnrolinfo> findUserEnrolinfoByDoctorInfo(String doctor,String ginfo);
	
	
	long countByExample(YhUserEnrolinfoExample example);

	int deleteByExample(YhUserEnrolinfoExample example);

	int deleteByPrimaryKey(Integer eUserId);

	int insert(YhUserEnrolinfo record);

	int insertSelective(YhUserEnrolinfo record);

	List<YhUserEnrolinfo> selectByExample(YhUserEnrolinfoExample example);

	YhUserEnrolinfo selectByPrimaryKey(Integer eUserId);

	YhUserEnrolinfo selectByPrimaryKeyByUsercode(String usercode);

	int updateByExampleSelective(@Param("record") YhUserEnrolinfo record,
			@Param("example") YhUserEnrolinfoExample example);

	int updateByExampleByusercodeForEval(@Param("record") YhUserEnrolinfo record);

	int updateByExample(@Param("record") YhUserEnrolinfo record, @Param("example") YhUserEnrolinfoExample example);

	int updateByPrimaryKeySelective(YhUserEnrolinfo record);

	int updateByPrimaryKey(YhUserEnrolinfo record);
	
	int modifyInfoByEva(YhUserEnrolinfo record);
	
	int updateByExampleSelectiveByusercode(Map<String, String> map);

}