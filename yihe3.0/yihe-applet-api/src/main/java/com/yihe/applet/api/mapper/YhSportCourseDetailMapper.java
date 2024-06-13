package com.yihe.applet.api.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhSportCourseDetail;

public interface YhSportCourseDetailMapper {
    int deleteByPrimaryKey(Integer cdetailId);

    int insert(YhSportCourseDetail record);

    int insertSelective(YhSportCourseDetail record);

    YhSportCourseDetail selectByPrimaryKey(Integer cdetailId);

    int updateByPrimaryKeySelective(YhSportCourseDetail record);

    int updateByPrimaryKey(YhSportCourseDetail record);
    
    List<YhSportCourseDetail> ShowTrainingcourse(int courseId);
    
    YhSportCourseDetail selectByDetailParticulars(Map<String, Object> map);
}