package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhHealthLectureCategoryApp;

public interface YhHealthLectureCategoryAppMapper {
    int deleteByPrimaryKey(Integer categoryid);

    int insert(YhHealthLectureCategoryApp record);

    int insertSelective(YhHealthLectureCategoryApp record);

    YhHealthLectureCategoryApp selectByPrimaryKey(Integer categoryid);

    int updateByPrimaryKeySelective(YhHealthLectureCategoryApp record);

    int updateByPrimaryKey(YhHealthLectureCategoryApp record);
    
    int findFabulousByInfoId(@Param("categoryId")Integer categoryId);
	
	int insertFabulous(@Param("categoryId")Integer categoryId,@Param("usercode")String usercode);
	
	int deleteFabulous(@Param("categoryId")Integer categoryId,@Param("usercode")String usercode);
	
	int findFabulousExistence(@Param("categoryId")Integer categoryId,@Param("usercode")String usercode);
	
	List<YhHealthLectureCategoryApp> findInfoAllByParent(YhHealthLectureCategoryApp yhHealthLectureCategoryApp);
	List<YhHealthLectureCategoryApp> findInfoAllByParam(YhHealthLectureCategoryApp yhHealthLectureCategoryApp);
	
}