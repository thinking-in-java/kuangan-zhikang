package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.sleepSkipApp;

public interface sleepSkipAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(sleepSkipApp record);

    int insertSelective(sleepSkipApp record);

    sleepSkipApp selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(sleepSkipApp record);

    int updateByPrimaryKey(sleepSkipApp record);
}