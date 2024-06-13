package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserWaistline;

public interface YhUserWaistlineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserWaistline record);

    int insertSelective(YhUserWaistline record);

    YhUserWaistline selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserWaistline record);

    int updateByPrimaryKey(YhUserWaistline record);
}