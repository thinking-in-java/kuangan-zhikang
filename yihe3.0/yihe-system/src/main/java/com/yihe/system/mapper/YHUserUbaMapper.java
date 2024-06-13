package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YHUserUba;

public interface YHUserUbaMapper {
	int deleteByPrimaryKey(Integer ubaId);

	int insert(YHUserUba record);

	int insertSelective(YHUserUba record);

	YHUserUba selectByPrimaryKey(Integer ubaId);

	YHUserUba selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserUba record);

	int updateByPrimaryKey(YHUserUba record);
	
	Map<String, Object> selectYHUserUba(String userCode);
	
	List<YHUserUba> selectPageByParam(String userCode);
    
	
	int selectByUbaCount();
}