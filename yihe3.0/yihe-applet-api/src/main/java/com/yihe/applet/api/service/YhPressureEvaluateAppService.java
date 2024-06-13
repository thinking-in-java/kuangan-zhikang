package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhPressureEvaluateApp;

import java.util.List;

public interface YhPressureEvaluateAppService {
	int deleteByPrimaryKey(Integer id);
	
    int insert(YhPressureEvaluateApp record);

    int insertSelective(YhPressureEvaluateApp record);

    YhPressureEvaluateApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhPressureEvaluateApp record);

    int updateByPrimaryKey(YhPressureEvaluateApp record);
    
    List<YhPressureEvaluateApp> selectAlList( Integer type, String userCode);
    
    List<YhPressureEvaluateApp> selectCount(String userCode);

    int assessCount(Integer type);

    YhPressureEvaluateApp findUserRecord(Integer type,String userCode);
}
