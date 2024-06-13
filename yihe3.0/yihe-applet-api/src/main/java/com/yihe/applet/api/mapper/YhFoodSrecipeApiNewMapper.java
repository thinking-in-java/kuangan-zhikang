package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhFoodSrecipeApiNew;


public interface YhFoodSrecipeApiNewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YhFoodSrecipeApiNew record);

    int insertSelective(YhFoodSrecipeApiNew record);

    YhFoodSrecipeApiNew selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YhFoodSrecipeApiNew record);

    int updateByPrimaryKeyWithBLOBs(YhFoodSrecipeApiNew record);

    int updateByPrimaryKey(YhFoodSrecipeApiNew record);
    
    List<YhFoodSrecipeApiNew> findNearFoodSrecipeInfo(@Param("diseaseid")Integer diseaseid,@Param("findEnergyNum")double findEnergyNum);
    
    List<YhFoodSrecipeApiNew> findNearFoodSrecipeInfoForJianKang(double findEnergyNum);
    
    List<YhFoodSrecipeApiNew> findFoodSrecipeInfoByMan();
    List<YhFoodSrecipeApiNew> findFoodSrecipeInfoByWuman();
}