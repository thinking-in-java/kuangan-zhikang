package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YHUserBloodPressure;

public interface YHUserBloodPressureMapper {
	int deleteByPrimaryKey(Integer uBloodpressureId);

	int insert(YHUserBloodPressure record);

	int insertSelective(YHUserBloodPressure record);

	YHUserBloodPressure selectByPrimaryKey(Integer uBloodpressureId);

	YHUserBloodPressure selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserBloodPressure record);

	int updateByPrimaryKey(YHUserBloodPressure record);

	String selectByPrimaryKeyByeidAndOrderby(String usercode);
	
	Map<String, Object> selectYHUserBloodPressure (String userCode);
	
	List<YHUserBloodPressure> selectPageByParam(String userCode);
    
    List<YHUserBloodPressure> selectPressure(Map<String, Object> map);
	
	int selectByBloodpressureCount();
	
	int selectUserBloodPressureCountByUserIdAndTime(Map<String, Object> params);
	
	 public int importYHUserBloodPressureData(@Param("Onlist")List<YHUserBloodPressure> Onlist);
}