package com.yihe.applet.api.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhSportEncourageApp;

public interface YhSportEncourageAppMapper {
    int deleteByPrimaryKey(Integer zwId);

    int insert(YhSportEncourageApp record);

    int insertSelective(YhSportEncourageApp record);

    YhSportEncourageApp selectByPrimaryKey(Integer zwId);

    int updateByPrimaryKeySelective(YhSportEncourageApp record);

    int updateByPrimaryKey(YhSportEncourageApp record);
    
    int showEncourageNum(Map<String, Object> map);
    
    List<YhSportEncourageApp> selectByChidSall(int chId);
}