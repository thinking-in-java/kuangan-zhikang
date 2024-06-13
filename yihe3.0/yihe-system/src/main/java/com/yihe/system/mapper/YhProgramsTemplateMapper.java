package com.yihe.system.mapper;

import com.yihe.system.domain.YhProgramsTemplate;

public interface YhProgramsTemplateMapper {
    int deleteByPrimaryKey(Integer programsId);

    int insert(YhProgramsTemplate record);

    int insertSelective(YhProgramsTemplate record);

    YhProgramsTemplate selectByPrimaryKey(Integer programsId);

    int updateByPrimaryKeySelective(YhProgramsTemplate record);

    int updateByPrimaryKey(YhProgramsTemplate record);
}