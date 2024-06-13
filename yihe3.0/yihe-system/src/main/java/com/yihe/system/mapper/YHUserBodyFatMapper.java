package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YHUserBodyFat;

public interface YHUserBodyFatMapper {
	int deleteByPrimaryKey(Integer wubfId);

	int insert(YHUserBodyFat record);

	int insertSelective(YHUserBodyFat record);

	YHUserBodyFat selectByPrimaryKey(Integer wubfId);

	YHUserBodyFat selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserBodyFat record);

	int updateByPrimaryKey(YHUserBodyFat record);
	
	Map<String, Object> selectYHUserBodyFat (String userCode);
	
	 List<YHUserBodyFat> selectPageByParam(String userCode);
	 
	 List<YHUserBodyFat> selectPageByParam1(String userCode);
	 
     
     List<YHUserBodyFat> selectBodyFat(String userCode);
	
	int selectByBodyFatCount();
}