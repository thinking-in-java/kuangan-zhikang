package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhSportStrength;

public interface YhSportStrengthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhSportStrength record);

    int insertSelective(YhSportStrength record);

    YhSportStrength selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportStrength record);

    int updateByPrimaryKey(YhSportStrength record);
    
    List<YhSportStrength> showSportStrength();
}