package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhSportGoals;

public interface YhSportGoalsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhSportGoals record);

    int insertSelective(YhSportGoals record);

    YhSportGoals selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportGoals record);

    int updateByPrimaryKey(YhSportGoals record);
    
    List<YhSportGoals> showSportGoals();
}