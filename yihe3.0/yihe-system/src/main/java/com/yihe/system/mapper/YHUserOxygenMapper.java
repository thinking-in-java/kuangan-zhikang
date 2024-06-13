package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YHUserOxygen;

public interface YHUserOxygenMapper {
	int deleteByPrimaryKey(Integer uOxygenId);

	int insert(YHUserOxygen record);

	int insertSelective(YHUserOxygen record);

	YHUserOxygen selectByPrimaryKey(Integer uOxygenId);

	YHUserOxygen selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserOxygen record);

	int updateByPrimaryKey(YHUserOxygen record);
	
	Map<String, Object> selectYHUserOxygen(String userCode);
	
	List<YHUserOxygen> selectPageByParam(String userCode);
    
    List<YHUserOxygen> selectPageYHUserOxygen(String userCode);
    
    List<YHUserOxygen> selectPageByheart(String userCode);
    
    List<YHUserOxygen> selectPageYHUserHeart(String userCode);
    
    
	
	int selectByOxygenCount();
}