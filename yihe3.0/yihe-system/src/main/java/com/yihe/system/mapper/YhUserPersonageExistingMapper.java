package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserPersonageExisting;

public interface YhUserPersonageExistingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageExisting record);

    int insertSelective(YhUserPersonageExisting record);

    YhUserPersonageExisting selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonageExisting record);

    int updateByPrimaryKey(YhUserPersonageExisting yhUserPersonageExisting);
}