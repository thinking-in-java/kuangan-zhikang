package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YHUserRenalfunction;

public interface YHUserRenalfunctionMapper {
	int deleteByPrimaryKey(Integer renalid);

	int insert(YHUserRenalfunction record);

	int insertSelective(YHUserRenalfunction record);

	YHUserRenalfunction selectByPrimaryKey(Integer renalid);

	YHUserRenalfunction selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserRenalfunction record);

	int updateByPrimaryKey(YHUserRenalfunction record);
	Map<String, Object> selectYHUserRenalfunction (String userCode);
	
	 List<YHUserRenalfunction> selectPageByParam(String userCode);
	
	int selectByRenalfunctionCount();
}