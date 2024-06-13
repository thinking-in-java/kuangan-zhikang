package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserMakeCardApp;

public interface YhUserMakeCardAppMapper {
    int deleteByPrimaryKey(String enrolCardId);

    int insert(YhUserMakeCardApp record);

    int insertSelective(YhUserMakeCardApp record);

    YhUserMakeCardApp selectByPrimaryKey(String enrolCardId);

    int updateByPrimaryKeySelective(YhUserMakeCardApp record);

    int updateByPrimaryKey(YhUserMakeCardApp record);
    
    YhUserMakeCardApp selectOrgIdParam(String userCode);
}