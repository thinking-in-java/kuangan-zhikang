package com.yihe.applet.api.service;

import com.github.pagehelper.PageInfo;
import com.yihe.applet.api.domain.YhHealthLectureInfoApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhHealthLectureInfoAppService {
	List<YhHealthLectureInfoApp> selectByExampleLimitParentComponents(String cateName,String usercode,Integer timeOrder, String title,Integer type,Integer categoryId,
																	  Integer curPage, Integer pageSize);
	List<YhHealthLectureInfoApp> selectByExampleLimit3Components(String cateName,String usercode,Integer timeOrder, String title,Integer type,Integer categoryId,
																 Integer curPage, Integer pageSize);


	List<YhHealthLectureInfoApp> findHealthLectureInfoByFoodClass(int categoryId);
	 YhHealthLectureInfoApp selectByPrimaryKey(Integer infoid);
	 List<YhHealthLectureInfoApp> selectByExample(YhHealthLectureInfoApp example);

	 PageInfo<YhHealthLectureInfoApp> findhealthLectureTitleAndTipsAndInstruction(String likeValue, int pageNum,
				int pageSize);
	 PageInfo<YhHealthLectureInfoApp> findhealthLectureByFoodSchool(int pageNum,
			 int pageSize);

		List<YhHealthLectureInfoApp> findInfoLimitNew3();

		List<YhHealthLectureInfoApp> selectByExampleLimit3(Integer categoryId, Integer curPage, Integer pageSize,String userCode,@Param("type") Integer type,
				Integer timeOrder,String title,List<String> orgList);
		
		List<YhHealthLectureInfoApp> selectByExampleLimit4(Integer categoryId, Integer curPage, Integer pageSize);

		List<YhHealthLectureInfoApp> selectByExampleLimitParent(Integer categoryId, Integer curPage, Integer pageSize,String userCode,Integer type,Integer timeOrder,String title,List<String> orgList);


		List<YhHealthLectureInfoApp> selectByExampleLimitAndLike(String like, Integer curPage, Integer pageSize);

}
