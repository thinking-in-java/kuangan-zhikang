package com.yihe.system.service;

import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.NewCoronaryHeartDiseasePatients;

import java.util.List;

public interface NewAnxietyDisorderInterventionRecordFormService {

    int insetNew(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm);

    int updateNew(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm);

    NewAnxietyDisorderInterventionRecordForm selectById(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm);

    List<NewAnxietyDisorderInterventionRecordForm> selectByPage(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm);

    int selectByPageCount(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm);


}
