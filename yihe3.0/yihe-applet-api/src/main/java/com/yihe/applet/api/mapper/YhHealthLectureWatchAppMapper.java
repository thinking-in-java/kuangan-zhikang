package com.yihe.applet.api.mapper;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhHealthLectureWatchApp;

public interface YhHealthLectureWatchAppMapper {
    int deleteByPrimaryKey(Integer watchid);

    int insert(YhHealthLectureWatchApp record);

    int insertSelective(YhHealthLectureWatchApp record);

    YhHealthLectureWatchApp selectByPrimaryKey(Integer watchid);

    int updateByPrimaryKeySelective(YhHealthLectureWatchApp record);

    int updateByPrimaryKey(YhHealthLectureWatchApp record);
    
    int update(YhHealthLectureWatchApp record);
    
    YhHealthLectureWatchApp selectclassIdByParam(@Param("userCode") String userCode,@Param("classId") Integer classId);
    
    YhHealthLectureWatchApp selectYhHealthLectureWatch(String classId);
    
    
    
}