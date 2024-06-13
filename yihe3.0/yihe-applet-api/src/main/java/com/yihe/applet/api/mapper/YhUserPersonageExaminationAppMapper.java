package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserPersonageExaminationApp;

public interface YhUserPersonageExaminationAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageExaminationApp record);

    int insertSelective(YhUserPersonageExaminationApp record);

    YhUserPersonageExaminationApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageExaminationApp record);

    int updateByPrimaryKey(YhUserPersonageExaminationApp record);
    YhUserPersonageExaminationApp showexamination(String userCode);
}