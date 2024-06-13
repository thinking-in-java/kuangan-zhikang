package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YHUserEcg;

public interface YHUserEcgMapper {
	int deleteByPrimaryKey(Integer uEcgId);

	int insert(YHUserEcg record);

	int insertSelective(YHUserEcg record);

	YHUserEcg selectByPrimaryKey(Integer uEcgId);

	YHUserEcg selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserEcg record);

	int updateByPrimaryKey(YHUserEcg record);

	Map<String, Object> selectYHUserEcg (String userCode);
	
	 List<YHUserEcg> selectPageByParam(String userCode);
	
	
	int selectByEcgCount();
}