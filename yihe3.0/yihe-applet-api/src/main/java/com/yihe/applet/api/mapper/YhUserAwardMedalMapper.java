package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserAwardMedal;

public interface YhUserAwardMedalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserAwardMedal record);

    int insertSelective(YhUserAwardMedal record);

    YhUserAwardMedal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserAwardMedal record);

    int updateByPrimaryKey(YhUserAwardMedal record); 
    List<YhUserAwardMedal> selectYhUserAwardMedal(String userCode);
}