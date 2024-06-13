package com.yihe.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhDiseaseCategory;
import com.yihe.system.domain.YhDiseaseCategoryExample;

public interface YhDiseaseCategoryMapper {
	List<YhDiseaseCategory> selectByPrimaryKeyByIsAssessment();
	
    long countByExample(YhDiseaseCategoryExample example);

    int deleteByExample(YhDiseaseCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YhDiseaseCategory record);

    int insertSelective(YhDiseaseCategory record);

    List<YhDiseaseCategory> selectByExample(YhDiseaseCategoryExample example);

    YhDiseaseCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YhDiseaseCategory record, @Param("example") YhDiseaseCategoryExample example);

    int updateByExample(@Param("record") YhDiseaseCategory record, @Param("example") YhDiseaseCategoryExample example);

    int updateByPrimaryKeySelective(YhDiseaseCategory record);

    int updateByPrimaryKey(YhDiseaseCategory record);
}