package com.yihe.system.mapper;


import com.yihe.system.domain.NewServiceRecordFormForHypertensivePatients;

import java.util.List;

public interface NewServiceRecordFormForHypertensivePatientsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewServiceRecordFormForHypertensivePatients row);

    int insertSelective(NewServiceRecordFormForHypertensivePatients row);

    NewServiceRecordFormForHypertensivePatients selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewServiceRecordFormForHypertensivePatients row);

    int updateByPrimaryKey(NewServiceRecordFormForHypertensivePatients row);

    List<NewServiceRecordFormForHypertensivePatients> selectByPage(NewServiceRecordFormForHypertensivePatients row);

    List<NewServiceRecordFormForHypertensivePatients> selectAll();

    int selectByPageCount(NewServiceRecordFormForHypertensivePatients row);
}