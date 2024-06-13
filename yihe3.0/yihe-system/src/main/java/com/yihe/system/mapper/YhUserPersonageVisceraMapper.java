package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserPersonageViscera;

public interface YhUserPersonageVisceraMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageViscera record);

    int insertSelective(YhUserPersonageViscera record);

    YhUserPersonageViscera selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonageViscera record);

    int updateByPrimaryKey(YhUserPersonageViscera yhUserPersonageViscera);
}