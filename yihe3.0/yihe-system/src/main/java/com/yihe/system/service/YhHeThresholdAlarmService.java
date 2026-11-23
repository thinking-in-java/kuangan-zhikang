package com.yihe.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhHeThresholdAlarm;
import com.yihe.system.domain.YhHeThresholdAlarmVOJO;

/**
* YhHeThresholdAlarmService接口
* Created by royiwen on 2020/3/27.
*/
public interface YhHeThresholdAlarmService {
	PageInfo<YhHeThresholdAlarmVOJO> findYhHeThresholdAlarmByDoctor(String usercode,String nameLike,String nametype,String status,String strTime,String endTime,int pageNum,int pageSize, Integer alertId);
	List<YhHeThresholdAlarmVOJO> findYhHeThresholdTypeByDoctor(String usercode);
	
	List<YhHeThresholdAlarmVOJO> findYhHeThresholdTypeByDoctorAndStatus(String usercode);
	
	YhHeThresholdAlarm selectByPrimaryKey(String id);
	
	YhHeThresholdAlarmVOJO findYhHeThresholdTypeByIdInfo(String id);
	
	int updateByPrimaryKeySelective(YhHeThresholdAlarm record);
	
	 int updateByPrimaryKeySelectiveBYVOJO(YhHeThresholdAlarmVOJO vojo);
}