package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserPersonageLiveApp;

public interface YhUserPersonageLiveAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageLiveApp record);

    int insertSelective(YhUserPersonageLiveApp record);

    YhUserPersonageLiveApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageLiveApp record);
    YhUserPersonageLiveApp showlive(String usercode);
    int updateByPrimaryKey(YhUserPersonageLiveApp record);
    
    
}