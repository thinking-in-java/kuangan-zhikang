package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserPersonageExamination;

public interface YhUserPersonageExaminationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageExamination record);

    int insertSelective(YhUserPersonageExamination record);

    YhUserPersonageExamination selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonageExamination record);

    int updateByPrimaryKey(YhUserPersonageExamination yhUserPersonageExamination);
}