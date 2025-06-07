package com.yihe.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserStepWeb;
 
public interface YhUserStepWebMapper {  
    int deleteByPrimaryKey(Integer stepId);

    int insert(YhUserStepWeb record);

    int insertSelective(YhUserStepWeb record);

    YhUserStepWeb selectByPrimaryKey(Integer stepId);

    int updateByPrimaryKeySelective(YhUserStepWeb record);

    int updateByPrimaryKey(YhUserStepWeb record);
    YhUserStepWeb selectUserIntegral(@Param("userCode") String userCode);

    YhUserStepWeb selectAggregate(String userCode);
}