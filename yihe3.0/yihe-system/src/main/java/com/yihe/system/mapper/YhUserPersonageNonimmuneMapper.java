package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhUserPersonageNonimmune;

public interface YhUserPersonageNonimmuneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageNonimmune yhUserPersonageNonimmune);

    int insertSelective(YhUserPersonageNonimmune record);

    List<YhUserPersonageNonimmune> selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(YhUserPersonageNonimmune record);

    int updateByPrimaryKey(YhUserPersonageNonimmune yhUserPersonageNonimmune);
}