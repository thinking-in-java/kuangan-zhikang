package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserPersonageExistingApp;

public interface YhUserPersonageExistingAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageExistingApp record);

    int insertSelective(YhUserPersonageExistingApp record);

    YhUserPersonageExistingApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageExistingApp record);

    int updateByPrimaryKey(YhUserPersonageExistingApp record);
    
    YhUserPersonageExistingApp showexisting(String userCode);
}