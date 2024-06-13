package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YHUserLiverfunction;

public interface YHUserLiverfunctionMapper {
	int deleteByPrimaryKey(Integer liverid);

	int insert(YHUserLiverfunction record);

	int insertSelective(YHUserLiverfunction record);

	YHUserLiverfunction selectByPrimaryKey(Integer liverid);

	YHUserLiverfunction selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserLiverfunction record);

	int updateByPrimaryKey(YHUserLiverfunction record);
	
	Map<String, Object> selectYHUserLiverfunction (String userCode);
	
	List<YHUserLiverfunction> selectPageByParam(String userCode);
	
	int selectByLiverfunctionCount();
}