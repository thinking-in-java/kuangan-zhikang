package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhDicIntroductionApp;

public interface YhDicIntroductionAppMapper {
    int deleteByPrimaryKey(Integer code);

    int insert(YhDicIntroductionApp record);

    int insertSelective(YhDicIntroductionApp record);

    YhDicIntroductionApp selectByPrimaryKey(Integer code);

    int updateByPrimaryKeySelective(YhDicIntroductionApp record);

    int updateByPrimaryKey(YhDicIntroductionApp record);
    YhDicIntroductionApp selectBmi (String name);
    YhDicIntroductionApp selectWHR(String name);
    YhDicIntroductionApp selectExercise (String name);
    YhDicIntroductionApp selecTnutrient (String name);
    YhDicIntroductionApp selectEverydayConsumption (String name);
}