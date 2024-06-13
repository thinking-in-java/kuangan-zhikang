package com.yihe.applet.api.service;



import com.yihe.applet.api.domain.YhHealthLectureWatchApp;

public interface YhHealthLectureWatchAppService {
	int insertSelective(YhHealthLectureWatchApp record);

    YhHealthLectureWatchApp selectByPrimaryKey(Integer watchid);

    int updateByPrimaryKeySelective(YhHealthLectureWatchApp record);

    int updateByPrimaryKey(YhHealthLectureWatchApp record);
    
    YhHealthLectureWatchApp selectYhHealthLectureWatch(String classId);
    
    int update(YhHealthLectureWatchApp record);
    
    YhHealthLectureWatchApp selectclassIdByParam( String userCode, Integer classId);
}
