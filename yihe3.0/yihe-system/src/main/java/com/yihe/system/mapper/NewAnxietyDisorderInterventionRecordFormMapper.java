package com.yihe.system.mapper;


import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.NewCoronaryHeartDiseasePatients;

import java.util.List;

public interface NewAnxietyDisorderInterventionRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewAnxietyDisorderInterventionRecordForm row);

    int insertSelective(NewAnxietyDisorderInterventionRecordForm row);

    NewAnxietyDisorderInterventionRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewAnxietyDisorderInterventionRecordForm row);

    int updateByPrimaryKey(NewAnxietyDisorderInterventionRecordForm row);

    List<NewAnxietyDisorderInterventionRecordForm> selectByPage(NewAnxietyDisorderInterventionRecordForm row);

    int selectByPageCount(NewAnxietyDisorderInterventionRecordForm row);
}