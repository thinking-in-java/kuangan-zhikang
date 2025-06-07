package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YHUserHeartRate;
import com.yihe.system.vo.AddNewLogDTO;

public interface YHUserHeartRateMapper {
	int deleteByPrimaryKey(Integer rid);

	int insert(YHUserHeartRate record);

	int insertSelective(YHUserHeartRate record);

	YHUserHeartRate selectByPrimaryKey(Integer rid);

	YHUserHeartRate selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserHeartRate record);

	int updateByPrimaryKey(YHUserHeartRate record);
	
	Map<String, Object> selectHeartRate(String userCode);

	Map<String, Object> checkHeartRateStatisticsByOrg(String orgId);
	Map<String, Object> checkOxygenStatisticsByOrg(String orgId);

	Map<String, Object> checkBloodGateStatisticsByOrg(String orgId);

	Map<String, Object> checkBloodSugarStatisticsByOrg(String orgId);

	Map<String, Object> checkBodyFatStatisticsByOrg(String orgId);
	Map<String, Object> checkSleepStatisticsByOrg(String orgId);

	List<String> checkExerciseStatistics(String orgId);


	List<AddNewLogDTO> queryTheNewMonitoringLogs(String orgId);
}