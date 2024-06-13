package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserRolePower;
import com.yihe.system.domain.YhUserRolePowerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YhUserRolePowerMapper {
    long countByExample(YhUserRolePowerExample example);

    int deleteByExample(YhUserRolePowerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YhUserRolePower record);

    int insertSelective(YhUserRolePower record);

    List<YhUserRolePower> selectByExample(YhUserRolePowerExample example);

    YhUserRolePower selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YhUserRolePower record, @Param("example") YhUserRolePowerExample example);

    int updateByExample(@Param("record") YhUserRolePower record, @Param("example") YhUserRolePowerExample example);

    int updateByPrimaryKeySelective(YhUserRolePower record);

    int updateByPrimaryKey(YhUserRolePower record);
    
    YhUserRolePower selectByPowerId(String userid);
    
    int updataByuserId(YhUserRolePower record);
   
}