package com.yihe.system.mapper;

import com.yihe.system.domain.YhSportFrequencyWeb;

public interface YhSportFrequencyWebMapper {
    int deleteByPrimaryKey(Integer frId);

    int insert(YhSportFrequencyWeb record);

    int insertSelective(YhSportFrequencyWeb record);

    YhSportFrequencyWeb selectByPrimaryKey(Integer frId);

    int updateByPrimaryKeySelective(YhSportFrequencyWeb record);

    int updateByPrimaryKey(YhSportFrequencyWeb record);
    
    YhSportFrequencyWeb showUserFrequency(String userCode);
}