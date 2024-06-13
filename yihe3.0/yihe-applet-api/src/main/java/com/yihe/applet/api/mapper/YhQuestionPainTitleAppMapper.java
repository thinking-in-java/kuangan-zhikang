package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhQuestionPainTitleApp;

public interface YhQuestionPainTitleAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhQuestionPainTitleApp record);

    int insertSelective(YhQuestionPainTitleApp record);

    YhQuestionPainTitleApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhQuestionPainTitleApp record);

    int updateByPrimaryKey(YhQuestionPainTitleApp record);
    
    List<YhQuestionPainTitleApp> selectAll();
}