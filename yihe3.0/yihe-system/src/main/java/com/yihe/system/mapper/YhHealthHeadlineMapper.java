package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhHealthHeadline;

public interface YhHealthHeadlineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhHealthHeadline record);

    int insertSelective(YhHealthHeadline record);

    YhHealthHeadline selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhHealthHeadline record);

    int updateByPrimaryKey(YhHealthHeadline record);
    
    List<YhHealthHeadline> selectByExample();
}