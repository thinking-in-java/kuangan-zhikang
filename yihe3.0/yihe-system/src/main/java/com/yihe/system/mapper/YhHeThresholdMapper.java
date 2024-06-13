package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhHeThreshold;
import com.yihe.system.domain.YhHeThresholdExample;

public interface YhHeThresholdMapper {
    long countByExample(YhHeThresholdExample example);

    int deleteByExample(YhHeThresholdExample example);

    int deleteByPrimaryKey(String id);

    int insert(YhHeThreshold record);

    int insertSelective(YhHeThreshold record);

    List<YhHeThreshold> selectByExample(YhHeThresholdExample example);

    YhHeThreshold selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") YhHeThreshold record, @Param("example") YhHeThresholdExample example);

    int updateByExample(@Param("record") YhHeThreshold record, @Param("example") YhHeThresholdExample example);

    int updateByPrimaryKeySelective(YhHeThreshold record);

    int updateByPrimaryKey(YhHeThreshold record);
    
    List<YhHeThreshold> selectallheath(); 
    
    int updatasetting(@Param("low")String low,@Param("high") String high,@Param("istrue")Integer istrue,@Param("name") String name);
    
    YhHeThreshold selectBythresType(String type);
}