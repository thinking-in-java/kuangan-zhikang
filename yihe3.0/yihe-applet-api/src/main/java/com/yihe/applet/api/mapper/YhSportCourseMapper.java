package com.yihe.applet.api.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhSportCourse;

public interface YhSportCourseMapper {
    int deleteByPrimaryKey(Integer courseId);

    int insert(YhSportCourse record);

    int insertSelective(YhSportCourse record);

    YhSportCourse selectByPrimaryKey(Integer courseId);

    int updateByPrimaryKeySelective(YhSportCourse record);

    int updateByPrimaryKey(YhSportCourse record);
    
    List<YhSportCourse> ShowTheSportsCourse(Map<String, Object> map);
    
    List<YhSportCourse> showCourseAdd(String userCode);
    
    int showCourseIdRepetition(Map<String, Object> map);
    
    List<YhSportCourse> selectdawdler(String courseUse);
    
    List<YhSportCourse> selectBySmall(Map<String, Object> map);
}