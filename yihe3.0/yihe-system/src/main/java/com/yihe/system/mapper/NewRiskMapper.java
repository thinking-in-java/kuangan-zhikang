package com.yihe.system.mapper;


import com.yihe.system.domain.NewRisk;

import java.util.List;

public interface NewRiskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewRisk row);

    int insertSelective(NewRisk row);

    NewRisk selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewRisk row);

    int updateByPrimaryKey(NewRisk row);

    List<NewRisk> selectByPage(NewRisk risk);

    int selectByPageCount(NewRisk risk);
}