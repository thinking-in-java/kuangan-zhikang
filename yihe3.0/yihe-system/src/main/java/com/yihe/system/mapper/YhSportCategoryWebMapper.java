package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhSportCategoryWeb;

public interface YhSportCategoryWebMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhSportCategoryWeb record);

    int insertSelective(YhSportCategoryWeb record);

    YhSportCategoryWeb selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportCategoryWeb record);

    int updateByPrimaryKey(YhSportCategoryWeb record);
    
    List<YhSportCategoryWeb> showSportCategory();
}