package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhFoodSrecipeNewWeb;
import com.yihe.system.domain.YhFoodSrecipeRecord;

public interface YhFoodSrecipeNewWebMapper {
	
	YhFoodSrecipeNewWeb findSrecipeById(Integer id);
	
	List<YhFoodSrecipeRecord> findFoodSreCipeByAllInfo(@Param("ftype")Integer ftype,@Param("eLevel")Integer eLevel,@Param("diseaseid")Integer diseaseid);
	
	
    int deleteByPrimaryKey(Long id);

    int insert(YhFoodSrecipeNewWeb record);

    int insertSelective(YhFoodSrecipeNewWeb record);

    YhFoodSrecipeNewWeb selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YhFoodSrecipeNewWeb record);

    int updateByPrimaryKeyWithBLOBs(YhFoodSrecipeNewWeb record);

    int updateByPrimaryKey(YhFoodSrecipeNewWeb record);
}