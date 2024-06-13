package com.yihe.applet.api.mapper;

import java.util.Map;

import com.yihe.applet.api.domain.YhUserSportCourseAdd;

public interface YhUserSportCourseAddMapper {
    int deleteByPrimaryKey(Map<String, Object> map);

    int insert(YhUserSportCourseAdd record);

    int insertSelective(YhUserSportCourseAdd record);

    YhUserSportCourseAdd selectByPrimaryKey(Integer addId);

    int updateByPrimaryKeySelective(YhUserSportCourseAdd record);

    int updateByPrimaryKey(YhUserSportCourseAdd record);
    
    YhUserSportCourseAdd selectMapCourse(Map<String, Object> map);
    
}