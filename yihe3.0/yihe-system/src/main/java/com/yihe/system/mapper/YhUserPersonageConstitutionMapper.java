package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserPersonageConstitution;

public interface YhUserPersonageConstitutionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageConstitution record);

    int insertSelective(YhUserPersonageConstitution record);

    YhUserPersonageConstitution selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonageConstitution record);

    int updateByPrimaryKey(YhUserPersonageConstitution yhUserPersonageConstitution);
}