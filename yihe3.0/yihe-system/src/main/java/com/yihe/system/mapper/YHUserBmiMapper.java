package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YHUserBmi;
import com.yihe.system.domain.YhUserPersonageCondition;

public interface YHUserBmiMapper { 
	int deleteByPrimaryKey(Integer uBmiId);

	int insert(YHUserBmi record);

	int insertSelective(YHUserBmi record);

	YHUserBmi selectByPrimaryKey(Integer uBmiId);

	YHUserBmi selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserBmi record);

	int updateByPrimaryKey(YHUserBmi record);
	
	Map<String, Object> selectYHUserBmi (String userCode);
	
	List<YHUserBmi> selectPageByParam(String userCode);
    
    List<YHUserBmi> selectBmiPage(String userCode);
	
	int selectByBmiCount();
	  public int importYHUserBmi(@Param("Onlist")List<YHUserBmi> Onlist);
}