package com.yihe.system.mapper;


import com.yihe.system.domain.NewRecordFormForStrokePatients;

import java.util.List;

public interface NewRecordFormForStrokePatientsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewRecordFormForStrokePatients row);

    int insertSelective(NewRecordFormForStrokePatients row);

    NewRecordFormForStrokePatients selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewRecordFormForStrokePatients row);

    int updateByPrimaryKey(NewRecordFormForStrokePatients row);

    List<NewRecordFormForStrokePatients> selectByPage(NewRecordFormForStrokePatients risk);

    List<NewRecordFormForStrokePatients> selectAll();

    int selectByPageCount(NewRecordFormForStrokePatients risk);
}