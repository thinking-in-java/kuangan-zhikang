package com.yihe.system.mapper;

import com.yihe.system.domain.YhSportSubject;

public interface YhSportSubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhSportSubject record);

    int insertSelective(YhSportSubject record);

    YhSportSubject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportSubject record);

    int updateByPrimaryKey(YhSportSubject record);
}