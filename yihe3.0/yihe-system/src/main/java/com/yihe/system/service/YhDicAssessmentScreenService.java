package com.yihe.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhDicAssessmentScreen;
import com.yihe.system.domain.YhDicAssessmentScreenExample;

/**
* YhDicAssessmentScreenService接口
* Created by dyb on 2020/3/23.
*/
public interface YhDicAssessmentScreenService{
	 long countByExample(YhDicAssessmentScreenExample example);

	    int deleteByExample(YhDicAssessmentScreenExample example);

	    int deleteByPrimaryKey(Integer sid);

	    int insert(YhDicAssessmentScreen record);

	    int insertSelective(YhDicAssessmentScreen record);

	    PageInfo<YhDicAssessmentScreen> selectByExample(YhDicAssessmentScreenExample example,int pageNum,int pageSize);

	    YhDicAssessmentScreen selectByPrimaryKey(Integer sid);

	    int updateByExampleSelective(@Param("record") YhDicAssessmentScreen record, @Param("example") YhDicAssessmentScreenExample example);

	    int updateByExample(@Param("record") YhDicAssessmentScreen record, @Param("example") YhDicAssessmentScreenExample example);

	    int updateByPrimaryKeySelective(YhDicAssessmentScreen record);

	    int updateByPrimaryKey(YhDicAssessmentScreen record);
}