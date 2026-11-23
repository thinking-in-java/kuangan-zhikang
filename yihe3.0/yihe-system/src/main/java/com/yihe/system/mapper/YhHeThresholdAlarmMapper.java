package com.yihe.system.mapper;

import com.yihe.system.domain.YhHeThresholdAlarm;
import com.yihe.system.domain.YhHeThresholdAlarmExample;
import com.yihe.system.domain.YhHeThresholdAlarmVOJO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YhHeThresholdAlarmMapper {
	
	List<YhHeThresholdAlarmVOJO> findYhHeThresholdAlarmByDoctor(@Param("usercode")String usercode,@Param("nameLike")String nameLike,@Param("nametype")String nametype,@Param("status")String status,@Param("strTime")String strTime,@Param("endTime")String endTime, @Param("alertId") Integer alertId);
	
	List<YhHeThresholdAlarmVOJO> findYhHeThresholdTypeByDoctor(@Param("usercode")String usercode);
	
	List<YhHeThresholdAlarmVOJO> findYhHeThresholdTypeByDoctorAndStatus(@Param("usercode")String usercode);
	
	YhHeThresholdAlarmVOJO findYhHeThresholdTypeByIdInfo(@Param("id")String id);
	
	
    long countByExample(YhHeThresholdAlarmExample example);

    int deleteByExample(YhHeThresholdAlarmExample example);

    int deleteByPrimaryKey(String id);

    int insert(YhHeThresholdAlarm record);

    int insertSelective(YhHeThresholdAlarm record);

    List<YhHeThresholdAlarm> selectByExample(YhHeThresholdAlarmExample example);

    YhHeThresholdAlarm selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") YhHeThresholdAlarm record, @Param("example") YhHeThresholdAlarmExample example);

    int updateByExample(@Param("record") YhHeThresholdAlarm record, @Param("example") YhHeThresholdAlarmExample example);

    int updateByPrimaryKeySelective(YhHeThresholdAlarm record);
    
    int updateByPrimaryKeySelectiveBYVOJO(YhHeThresholdAlarmVOJO vojo);

    int updateByPrimaryKey(YhHeThresholdAlarm record);
}