package com.yihe.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhDiseaseCategory;
import com.yihe.system.domain.YhDiseaseCategoryExample;

/**
 * YhDiseaseCategoryService接口 Created by dyb on 2020/3/19.
 */
public interface YhDiseaseCategoryService {

	long countByExample(YhDiseaseCategoryExample example);

	int deleteByExample(YhDiseaseCategoryExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(YhDiseaseCategory record);

	int insertSelective(YhDiseaseCategory record);

	List<YhDiseaseCategory> selectByExample(YhDiseaseCategoryExample example);

	YhDiseaseCategory selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") YhDiseaseCategory record,
			@Param("example") YhDiseaseCategoryExample example);

	int updateByExample(@Param("record") YhDiseaseCategory record, @Param("example") YhDiseaseCategoryExample example);

	int updateByPrimaryKeySelective(YhDiseaseCategory record);

	int updateByPrimaryKey(YhDiseaseCategory record);

}