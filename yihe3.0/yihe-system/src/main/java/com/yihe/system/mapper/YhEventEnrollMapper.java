package com.yihe.system.mapper;

import com.yihe.system.domain.YhEventEnroll;
import com.yihe.system.domain.YhEventEnrollExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface YhEventEnrollMapper {
    long countByExample(YhEventEnrollExample example);

    int deleteByExample(YhEventEnrollExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YhEventEnroll record);

    int insertSelective(YhEventEnroll record);

    List<YhEventEnroll> selectByExample();

    YhEventEnroll selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YhEventEnroll record);

    int updateByExample(@Param("record") YhEventEnroll record, @Param("example") YhEventEnrollExample example);

    int updateByPrimaryKeySelective(YhEventEnroll record);

    int updateByPrimaryKey(YhEventEnroll record);
    
    List<YhEventEnroll> selectEnrollAll(Map<String, Object> map);
    
    int selectEnrollAllConut(Map<String, Object> map);
    
    int selctEventNmaeRepetition(Map<String, Object> map);
}