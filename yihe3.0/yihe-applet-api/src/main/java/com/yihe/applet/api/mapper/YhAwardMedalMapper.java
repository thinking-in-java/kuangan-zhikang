package com.yihe.applet.api.mapper;

import java.util.Map;

import com.yihe.applet.api.domain.YhAwardMedal;

public interface YhAwardMedalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhAwardMedal record);

    int insertSelective(YhAwardMedal record);

    YhAwardMedal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhAwardMedal record);

    int updateByPrimaryKey(YhAwardMedal record);
    
    YhAwardMedal selectdengMedal(Map<String, Object> map);
}