package com.yihe.system.mapper;

import com.yihe.system.domain.YhSportWeekly;

public interface YhSportWeeklyMapper {
    int deleteByPrimaryKey(Integer weId);

    int insert(YhSportWeekly record);

    int insertSelective(YhSportWeekly record);

    YhSportWeekly selectByPrimaryKey(Integer weId);

    int updateByPrimaryKeySelective(YhSportWeekly record);

    int updateByPrimaryKey(YhSportWeekly record);
}