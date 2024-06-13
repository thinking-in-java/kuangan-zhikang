package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserPersonageConstitutionApp;

public interface YhUserPersonageConstitutionAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageConstitutionApp record);

    int insertSelective(YhUserPersonageConstitutionApp record);

    YhUserPersonageConstitutionApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageConstitutionApp record);

    int updateByPrimaryKey(YhUserPersonageConstitutionApp record);
    YhUserPersonageConstitutionApp showconstitution(String userCode);
   
}