package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YHUserOtherfunction;

public interface YHUserOtherfunctionMapper {
	int deleteByPrimaryKey(Integer otherid);

	int insert(YHUserOtherfunction record);

	int insertSelective(YHUserOtherfunction record);

	YHUserOtherfunction selectByPrimaryKey(Integer otherid);

	YHUserOtherfunction selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserOtherfunction record);

	int updateByPrimaryKey(YHUserOtherfunction record);
	
	Map<String, Object> selectYHUserOtherfunction (String userCode);
	
	List<YHUserOtherfunction> selectPageByParam(String userCode);
	
	int selectByOtherfunctionCount();
}