package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YHUserBloodsuger;

public interface YHUserBloodsugerMapper {
	int deleteByPrimaryKey(Integer uBloodsugerId);

	int insert(YHUserBloodsuger record);

	int insertSelective(YHUserBloodsuger record);

	YHUserBloodsuger selectByPrimaryKey(Integer uBloodsugerId);

	YHUserBloodsuger selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserBloodsuger record);

	int updateByPrimaryKey(YHUserBloodsuger record);
	
	Map<String, Object> selectYHUserBloodsuger (String userCode);
	
	 List<YHUserBloodsuger> selectPageByParam(String userCode);
     
     List<YHUserBloodsuger> selectBloodsuger(Map<String, Object> map);

	int selectByBloodsugerCount();
	
	  public int importYHUserBloodsugerData(@Param("Onlist")List<YHUserBloodsuger> Onlist);
}