package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.InterventionAdjustment;
import com.yihe.system.domain.YhFoodSrecipeRecord;

public interface YhFoodSrecipeRecordMapper {
	
	String findFoodSrecipeRecordByRsInfo(String rs);
	
	YhFoodSrecipeRecord findTypeoneNewInfo(String usercode);
	
	
	YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNew(String usercode);
	
	YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNewISInfo(String usercode);
	
	int modifyFoodSrecipeRecordByUserCodeForType2(String usercode);
	
	List<String> findElevelInfoGroupBy();
	
	String findElemtInfoByFoodSrecipe(@Param("usercode")String usercode,@Param("elemt")String elemt);

	List<InterventionAdjustment> findInterventionAdjustmentInfo(Map<String, Object> map);

	List<String> findInterventionAdjustmentInfo2(Map<String, Object> map);

    int deleteByPrimaryKey(Integer recordId);

    int insert(YhFoodSrecipeRecord record);

    int insertSelective(YhFoodSrecipeRecord record);

    YhFoodSrecipeRecord selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(YhFoodSrecipeRecord record);
    
    int updateByPrimaryKeySelective1(YhFoodSrecipeRecord record);

    int updateByPrimaryKey(YhFoodSrecipeRecord record);
    
    List<YhFoodSrecipeRecord> selectSrecipeRecord(Map<String, Object> map);
    
    int selectSrecipeRecordConut(Map<String, Object> map);
    
    YhFoodSrecipeRecord selectNewest(String userCode);
    
    String findRecordKayForTime(String usercode);
    
    String findRecordKayForTimeAndType2(@Param("usercode")String usercode,@Param("time")String time);
}