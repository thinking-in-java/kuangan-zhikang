package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.TestingVOJO;
import com.yihe.system.domain.YHUserTemperature;

public interface YHUserTemperatureMapper {
	int deleteByPrimaryKey(Integer tid);

	int insert(YHUserTemperature record);

	int insertSelective(YHUserTemperature record);

	YHUserTemperature selectByPrimaryKey(Integer tid);

	YHUserTemperature selectByPrimaryKeyByUsercodeLimit(String usercode);
	
	List<TestingVOJO> selectByPrimaryKeyForInfoNameTiwen(@Param("usercode")String usercode,@Param("nameLike")String nameLike,@Param("usernameLike")String usernameLike,@Param("endTime")String endTime,@Param("strTime")String strTime,@Param("TypeName")String TypeName);
	
	
	List<YHUserTemperature> selectByPrimaryKeyForInfoNum(@Param("usercode")String usercode,@Param("nameLike")String nameLike,@Param("username")String username,@Param("endTime")String endTime,@Param("strTime")String strTime);
	List<TestingVOJO> selectByPrimaryKeyForInfoName(@Param("usercode")String usercode,@Param("nameLike")String nameLike,@Param("usernameLike")String usernameLike,@Param("endTime")String endTime,@Param("strTime")String strTime,@Param("TypeName")String TypeName);
	List<TestingVOJO> selectByPrimaryKeyForInfoName2(@Param("usercode")String usercode,@Param("nameLike")String nameLike,@Param("usernameLike")String usernameLike,@Param("endTime")String endTime,@Param("strTime")String strTime,@Param("TypeName")String TypeName,@Param("pageNum")int pageNum,@Param("pageSize")int pageSize);

	int updateByPrimaryKeySelective(YHUserTemperature record);

	int updateByPrimaryKey(YHUserTemperature record);
	
	Map<String, Object> selectUserAnimalHeat (String userCode);
	
	  List<YHUserTemperature> selectPageByParam(String userCode);
	  
	  List<YHUserTemperature> selectTemperature(String userCode);
	  
	  

	int selectByTemperatureCount();
}