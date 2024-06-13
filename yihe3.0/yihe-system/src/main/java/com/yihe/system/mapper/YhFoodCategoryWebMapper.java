package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhFoodCategoryWeb;

public interface YhFoodCategoryWebMapper {
	List<YhFoodCategoryWeb> findYhFoodCategoryByAnalysis();
	
	List<YhFoodCategoryWeb> findYhFoodCategoryAll();
	
    int deleteByPrimaryKey(Integer id);

    int insert(YhFoodCategoryWeb record);

    int insertSelective(YhFoodCategoryWeb record);

    YhFoodCategoryWeb selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhFoodCategoryWeb record);

    int updateByPrimaryKey(YhFoodCategoryWeb record);
}