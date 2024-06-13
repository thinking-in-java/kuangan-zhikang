package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhSportFrequencyApp;

public interface YhSportFrequencyAppMapper {
    int deleteByPrimaryKey(Integer frId);

    int insert(YhSportFrequencyApp record);

    int insertSelective(YhSportFrequencyApp record);

    YhSportFrequencyApp selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhSportFrequencyApp record);

    int updateByPrimaryKey(YhSportFrequencyApp record);
}