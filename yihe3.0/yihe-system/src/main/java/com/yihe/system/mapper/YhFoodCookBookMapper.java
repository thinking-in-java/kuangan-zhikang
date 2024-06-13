package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhFoodCookBook;

public interface YhFoodCookBookMapper {

    List<String> findDoctorInfo(String org);
    List<String> findjigouInfo(String org);

	 List<YhFoodCookBook> findFoodCookBookByNo10(@Param("val")String val);
    List<YhFoodCookBook> findCookBookInfoByType(@Param("ftype")Integer ftype,@Param("val")String val);
	
    int deleteByPrimaryKey(Integer fCId);

    int insert(YhFoodCookBook record);

    int insertSelective(YhFoodCookBook record);

    YhFoodCookBook selectByPrimaryKey(Integer fCId);
    
    

    YhFoodCookBook selectByPrimaryKeyOld(Integer fCId);
    
    int updateByPrimaryKeySelective(YhFoodCookBook record);

    int updateByPrimaryKey(YhFoodCookBook record);
    
    List<YhFoodCookBook> selectCookBook(Map<String,Object> map);
    
    int selectCookBookConut(Map<String,Object> map);
    
    int selctFcNameRepetition(Map<String,Object> map);
}