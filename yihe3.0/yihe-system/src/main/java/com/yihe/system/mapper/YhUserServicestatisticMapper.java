package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhUserServicestatistic;

public interface YhUserServicestatisticMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserServicestatistic record);

    int insertSelective(YhUserServicestatistic record);

    YhUserServicestatistic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserServicestatistic record);

    int updateByPrimaryKey(YhUserServicestatistic record);
    
    List<YhUserServicestatistic> selectervicestatistic();
}