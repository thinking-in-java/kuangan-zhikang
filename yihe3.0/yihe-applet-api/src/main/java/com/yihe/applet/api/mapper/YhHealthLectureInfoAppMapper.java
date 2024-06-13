package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhHealthLectureInfoApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhHealthLectureInfoAppMapper {
	
	List<YhHealthLectureInfoApp> findHealthLectureInfoByFoodClass(int categoryId);
	
	List<YhHealthLectureInfoApp> findHealthLectureInfoBySportClass(int categoryId);
	
    int deleteByPrimaryKey(Integer infoid);

    int insert(YhHealthLectureInfoApp record);

    int insertSelective(YhHealthLectureInfoApp record);

    YhHealthLectureInfoApp selectByPrimaryKey(Integer infoid);

    int updateByPrimaryKeySelective(YhHealthLectureInfoApp record);

    int updateByPrimaryKey(YhHealthLectureInfoApp record);
    
    List<YhHealthLectureInfoApp> selectByExample(YhHealthLectureInfoApp example);

	List<YhHealthLectureInfoApp> findhealthLectureTitleAndTipsAndInstruction(@Param("likeValue") String likeValue);
	
	List<YhHealthLectureInfoApp> findhealthLectureByFoodSchool();

	List<YhHealthLectureInfoApp> selectByExampleLimit3(@Param("categoryId") Integer categoryId,
			@Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize,@Param("userCode") String userCode,@Param("type") Integer type,
			@Param("timeOrder") Integer timeOrder,@Param("title") String title,@Param("orgList")List<String> orgList);

	List<YhHealthLectureInfoApp> selectByExampleLimit4(@Param("categoryId") Integer categoryId,
			@Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize);
	
	List<YhHealthLectureInfoApp> selectByExampleLimitParent(@Param("categoryId") Integer categoryId,
			@Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize,@Param("userCode") String userCode,@Param("type") Integer type,
			@Param("timeOrder") Integer timeOrder,@Param("title") String title,@Param("orgList")List<String> orgList);
	
	
	List<YhHealthLectureInfoApp> selectByExampleLimitParentComponents(@Param("cateName")String cateName,@Param("usercode") String usercode,@Param("timeOrder") Integer timeOrder,@Param("title") String title,@Param("type") Integer type,@Param("categoryId") Integer categoryId,
																	  @Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize);
List<YhHealthLectureInfoApp> selectByExampleLimit3Components(@Param("cateName")String cateName,@Param("usercode") String usercode,@Param("timeOrder") Integer timeOrder,@Param("title") String title,@Param("type") Integer type,@Param("categoryId") Integer categoryId,
																	  @Param("curPage") Integer curPage, @Param("pageSize") Integer pageSize);


	List<YhHealthLectureInfoApp> findInfoLimitNew3();

	List<YhHealthLectureInfoApp> selectByExampleLimitAndLike(@Param("like") String like, @Param("curPage") Integer curPage,
			@Param("pageSize") Integer pageSize);
	
	YhHealthLectureInfoApp selectByHealthLectureInfo(Integer categoryId);
	
}