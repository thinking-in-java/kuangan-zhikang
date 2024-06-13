package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserPersonageVisceraApp;

public interface YhUserPersonageVisceraAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageVisceraApp record);

    int insertSelective(YhUserPersonageVisceraApp record);

    YhUserPersonageVisceraApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageVisceraApp record);

    int updateByPrimaryKey(YhUserPersonageVisceraApp record);
    
    YhUserPersonageVisceraApp showviscera(String userCode);
}