package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserPersonagePhysicalApp;

public interface YhUserPersonagePhysicalAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonagePhysicalApp record);

    int insertSelective(YhUserPersonagePhysicalApp record);

    YhUserPersonagePhysicalApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonagePhysicalApp record);

    int updateByPrimaryKey(YhUserPersonagePhysicalApp record);
    
    YhUserPersonagePhysicalApp showphysical(String userCode);
}