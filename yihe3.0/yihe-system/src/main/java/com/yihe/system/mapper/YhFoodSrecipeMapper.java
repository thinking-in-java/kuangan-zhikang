package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhFoodSrecipe;

public interface YhFoodSrecipeMapper {
	
	int findSrecipeByfsDescribeCount(String fsDescribe);
	
	List<YhFoodSrecipe> findFoodPackageManagement(@Param("oneself")Integer oneself,@Param("usercode")String usercode,@Param("ftype")Integer ftype,@Param("elevel")Integer elevel,@Param("diseaseid")Integer diseaseid,@Param("fsDescribe")String fsDescribe);
	
	int insertAll(Map<String, Object> map);
	
    int deleteByPrimaryKey(Integer fsId);

    int insert(YhFoodSrecipe record);

    int insertSelective(YhFoodSrecipe record);

    YhFoodSrecipe selectByPrimaryKey(Integer fsId);
    
    YhFoodSrecipe findFoodSrecipeByFsId(Integer fsId);

    int updateByPrimaryKeySelective(YhFoodSrecipe record);

    int updateByPrimaryKeyWithBLOBs(YhFoodSrecipe record);

    int updateByPrimaryKey(YhFoodSrecipe record);
    
    List<YhFoodSrecipe> selectSrecipe(Map<String, Object> map);
    
    List<YhFoodSrecipe> findSrecipeAllInfo();
    
    int selectSrecipeConut(Map<String, Object> map);
    
    List<YhFoodSrecipe> selectSrecipeshow(Map<String, Object> map);
    
    YhFoodSrecipe findYhFoodSrecipeByDtypeAndSexAndNumAndDis(@Param("type")Integer type,@Param("sex")Integer sex,@Param("number")Integer number,@Param("diseaseid")String diseaseid);
    
    YhFoodSrecipe findYhFoodSrecipeByDtypeAndSexAndNumAndDisNew(@Param("type")Integer type,@Param("sex")Integer sex,@Param("number")Integer number,@Param("diseaseid")String diseaseid,@Param("isUse")int isUse);
}