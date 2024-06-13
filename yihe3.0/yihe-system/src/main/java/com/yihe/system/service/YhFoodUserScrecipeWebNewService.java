package com.yihe.system.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhFoodUserScrecipeWebNew;

public interface YhFoodUserScrecipeWebNewService {
	
	List<YhFoodUserScrecipeWebNew> findFoodUserScrecipe2ByIdList(List<Integer> id);
	
	   YhFoodUserScrecipeWebNew selectByPrimaryKey(Integer uFoodErecipeId);

	   
	Integer findMaxSrecipeId();
	
	 int updateByPrimaryKeySelective(YhFoodUserScrecipeWebNew record);
	
	 int insertSelective(YhFoodUserScrecipeWebNew record);
	 
	 
	List<String> findFoodUserScrecipeGroupLevel();
	
	PageInfo<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo(Integer oneself,String usercode,Integer eLevel,String diseaseid,Integer pageNum,Integer pageSize);
	PageInfo<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo2(Integer oneself,String usercode,Integer eLevel,List<String> diseaseid,Integer pageNum,Integer pageSize);
}
