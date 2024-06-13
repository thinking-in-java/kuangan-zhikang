package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserPersonageCondition;

public interface YhUserPersonageConditionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageCondition record);

    int insertSelective(YhUserPersonageCondition record);

    YhUserPersonageCondition selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonageCondition record);

    int updateByPrimaryKeySelective2(YhUserPersonageCondition record);

    int updateByPrimaryKey(YhUserPersonageCondition yhUserPersonageCondition);
    
    public int importYhUserPersonageConditionData(@Param("Onlist")List<YhUserPersonageCondition> Onlist);
    
    YhUserPersonageCondition showcondition(String userCode);

}