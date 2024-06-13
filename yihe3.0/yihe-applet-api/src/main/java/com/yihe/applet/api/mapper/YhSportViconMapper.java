package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhSportVicon;

public interface YhSportViconMapper {
    int deleteByPrimaryKey(Integer viconId);

    int insert(YhSportVicon record);

    int insertSelective(YhSportVicon record);

    YhSportVicon selectByPrimaryKey(Integer viconId);

    int updateByPrimaryKeySelective(YhSportVicon record);

    int updateByPrimaryKey(YhSportVicon record);
}