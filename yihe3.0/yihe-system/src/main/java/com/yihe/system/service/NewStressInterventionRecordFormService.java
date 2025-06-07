package com.yihe.system.service;

import com.yihe.system.domain.NewObsessiveCompulsiveDisorderInterventionRecordForm;
import com.yihe.system.domain.NewStressInterventionRecordForm;

import java.util.List;

public interface NewStressInterventionRecordFormService {
    int insetNew(NewStressInterventionRecordForm newStressInterventionRecordForm);

    int updateNew(NewStressInterventionRecordForm newStressInterventionRecordForm);

    NewStressInterventionRecordForm selectById(NewStressInterventionRecordForm newStressInterventionRecordForm);

    List<NewStressInterventionRecordForm> selectByPage(NewStressInterventionRecordForm newStressInterventionRecordForm);

    int selectByPageCount(NewStressInterventionRecordForm newStressInterventionRecordForm);
}
