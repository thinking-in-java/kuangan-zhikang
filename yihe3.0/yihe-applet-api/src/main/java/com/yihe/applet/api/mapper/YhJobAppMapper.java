package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhJobApp;
import com.yihe.applet.api.domain.YhJobAppKey;

public interface YhJobAppMapper {
    int deleteByPrimaryKey(YhJobAppKey key);

    int insert(YhJobApp record);

    int insertSelective(YhJobApp record);

    YhJobApp selectByPrimaryKey(YhJobAppKey key);

    int updateByPrimaryKeySelective(YhJobApp record);

    int updateByPrimaryKey(YhJobApp record);
}