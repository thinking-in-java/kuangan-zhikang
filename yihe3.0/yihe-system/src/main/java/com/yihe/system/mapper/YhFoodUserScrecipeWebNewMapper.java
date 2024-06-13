package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhFoodUserScrecipeWebNew;

public interface YhFoodUserScrecipeWebNewMapper {
	
	List<YhFoodUserScrecipeWebNew> findFoodUserScrecipe2ByIdList(List<Integer> id);
	
	Integer findMaxSrecipeId();
	
	List<String> findFoodUserScrecipeGroupLevel();
	
	List<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo(@Param("oneself")Integer oneself,@Param("usercode")String usercode,@Param("eLevel")Integer eLevel,@Param("diseaseid")String diseaseid);
	List<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo2(@Param("oneself")Integer oneself,@Param("usercode")String usercode,@Param("eLevel")Integer eLevel,@Param("diseaseid")List<String> diseaseid,@Param("idlist")List<Integer> idlist);
	
    int deleteByPrimaryKey(Integer uFoodErecipeId);

    int insert(YhFoodUserScrecipeWebNew record);

    int insertSelective(YhFoodUserScrecipeWebNew record);

    YhFoodUserScrecipeWebNew selectByPrimaryKey(Integer uFoodErecipeId);

    int updateByPrimaryKeySelective(YhFoodUserScrecipeWebNew record);

    int updateByPrimaryKeyWithBLOBs(YhFoodUserScrecipeWebNew record);

    int updateByPrimaryKey(YhFoodUserScrecipeWebNew record);
}