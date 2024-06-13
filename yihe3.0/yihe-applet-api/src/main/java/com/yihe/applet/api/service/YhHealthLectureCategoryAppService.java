package com.yihe.applet.api.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhHealthLectureCategoryApp;

public interface YhHealthLectureCategoryAppService {
	  YhHealthLectureCategoryApp selectByPrimaryKey(Integer categoryid);
	  	int findFabulousByInfoId(Integer categoryId);
		
		int insertFabulous(Integer categoryId,String usercode);
		
		int deleteFabulous(Integer categoryId,String usercode);
		
		int findFabulousExistence(Integer categoryId,String usercode);
		
		List<YhHealthLectureCategoryApp> findInfoAllByParent(YhHealthLectureCategoryApp yhHealthLectureCategoryApp);
}
