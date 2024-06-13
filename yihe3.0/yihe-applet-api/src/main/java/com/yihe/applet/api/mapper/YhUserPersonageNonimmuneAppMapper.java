package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserPersonageNonimmuneApp;

public interface YhUserPersonageNonimmuneAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageNonimmuneApp record);

    int insertSelective(YhUserPersonageNonimmuneApp record);

    YhUserPersonageNonimmuneApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageNonimmuneApp record);

    int updateByPrimaryKey(YhUserPersonageNonimmuneApp record);
    List<YhUserPersonageNonimmuneApp> shownonimmune(String userCode);
}