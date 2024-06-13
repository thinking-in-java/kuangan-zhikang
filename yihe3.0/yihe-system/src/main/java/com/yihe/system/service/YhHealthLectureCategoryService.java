package com.yihe.system.service;

import java.util.List;

import com.yihe.system.domain.YhHealthLectureCategory;

/**
* YhHealthLectureCategoryService接口
* Created by dong on 2020/3/26.
*/
public interface YhHealthLectureCategoryService {

	/**
	 * 查询课堂分类所有内容
	 * @return
	 */
	 List<YhHealthLectureCategory> showcategory();
	 
	 /**
	  * 删除子类
	  * @param parentId
	  * @return
	  */
	 int deleteByparentId(String parentId);
	 
	  List<YhHealthLectureCategory> showcategoryparentId(String parentId);

	int updateByPrimaryKey(YhHealthLectureCategory ory);

	int deleteByPrimaryKey(int id);

	int insertSelective(YhHealthLectureCategory cate);

	List<YhHealthLectureCategory> showpaid();
	
	  int updateByPrimaryKeySelective(YhHealthLectureCategory record);
	  
	   List<YhHealthLectureCategory> showcategoryyiji();
	   
	   YhHealthLectureCategory selectByPrimaryKey(Integer categoryid);
}