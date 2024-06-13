package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserPersonageLive;

public interface YhUserPersonageLiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageLive record);

    int insertSelective(YhUserPersonageLive record);

    YhUserPersonageLive selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonageLive record);

    int updateByPrimaryKey(YhUserPersonageLive yhUserPersonageLive);
}