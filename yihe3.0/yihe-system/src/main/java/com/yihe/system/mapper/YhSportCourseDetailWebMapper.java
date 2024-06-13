package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhSportCourseDetailWeb;

public interface YhSportCourseDetailWebMapper {
    int deleteByPrimaryKey(Integer cdetailId);

    int insert(YhSportCourseDetailWeb record);

    int insertSelective(YhSportCourseDetailWeb record);

    YhSportCourseDetailWeb selectByPrimaryKey(Integer cdetailId);

    int updateByPrimaryKeySelective(YhSportCourseDetailWeb record);

    int updateByPrimaryKey(YhSportCourseDetailWeb record);
    
List<YhSportCourseDetailWeb> selectSportVicon(Map<String, Object> map);
    
    int selectSportViconCount(Map<String, Object> map);
}