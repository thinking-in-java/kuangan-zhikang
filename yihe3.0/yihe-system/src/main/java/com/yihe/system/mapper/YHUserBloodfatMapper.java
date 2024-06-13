package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YHUserBloodfat;

public interface YHUserBloodfatMapper {
	int deleteByPrimaryKey(Integer uBloodfatId);

	int insert(YHUserBloodfat record);

	int insertSelective(YHUserBloodfat record);

	YHUserBloodfat selectByPrimaryKey(Integer uBloodfatId);

	YHUserBloodfat selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserBloodfat record);

	int updateByPrimaryKey(YHUserBloodfat record);

	Map<String, Object> selectYHUserBloodfat (String userCode);
	
	 List<YHUserBloodfat> selectPageByParam(String userCode);
	
	int selectByBloodfatCount();
	
	 public int importYHUserBloodfatData(@Param("Onlist")List<YHUserBloodfat> Onlist);
}