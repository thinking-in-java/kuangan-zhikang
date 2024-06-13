package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserServicestatisticApp;

public interface YhUserServicestatisticAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserServicestatisticApp record);

    int insertSelective(YhUserServicestatisticApp record);

    YhUserServicestatisticApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserServicestatisticApp record);

    int updateByPrimaryKey(YhUserServicestatisticApp record);
    
    YhUserServicestatisticApp selectByPrimaryKeyGenre(String genre);
}