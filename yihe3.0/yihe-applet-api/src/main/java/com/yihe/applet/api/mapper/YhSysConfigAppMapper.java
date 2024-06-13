package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhSysConfigApp;

public interface YhSysConfigAppMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YhSysConfigApp record);

    int insertSelective(YhSysConfigApp record);

    YhSysConfigApp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YhSysConfigApp record);

    int updateByPrimaryKey(YhSysConfigApp record);
    YhSysConfigApp selectYhSysConfig();
}