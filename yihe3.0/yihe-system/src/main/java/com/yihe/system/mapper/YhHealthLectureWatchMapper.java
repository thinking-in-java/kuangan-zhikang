package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhHealthLectureWatch;

public interface YhHealthLectureWatchMapper {
	int findHealthLectureWatchByUsercode(String usercode);
	
    int deleteByPrimaryKey(Integer watchid);
 
    int insert(YhHealthLectureWatch record);

    int insertSelective(YhHealthLectureWatch record);

    YhHealthLectureWatch selectByPrimaryKey(Integer watchid);

    int updateByPrimaryKeySelective(YhHealthLectureWatch record);

    int updateByPrimaryKey(YhHealthLectureWatch record);
    
    List<Map<String, Object>>  selectexportClassroomData(@Param("infoid") Integer infoid, @Param("categoryId") Integer categoryId);
    
    List<Map<String, Object>>  selecteAllClassroomData();
    
    List<Map<String, Object>>  selecteYesterdayAllClassroomData();
    
    List<Map<String, Object>>  selecteThisweekAllClassroomData();
    
    List<Map<String, Object>> selectelaskweekAllClassroomData();
    
    int showwatchnum(int classId);
    
}