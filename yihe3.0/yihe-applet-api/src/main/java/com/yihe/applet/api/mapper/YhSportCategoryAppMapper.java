package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhSportCategoryApp;

public interface YhSportCategoryAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhSportCategoryApp record);

    int insertSelective(YhSportCategoryApp record);

    YhSportCategoryApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportCategoryApp record);

    int updateByPrimaryKey(YhSportCategoryApp record);
}