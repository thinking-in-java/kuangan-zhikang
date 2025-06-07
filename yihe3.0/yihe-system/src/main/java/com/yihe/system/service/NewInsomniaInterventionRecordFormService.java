package com.yihe.system.service;

import com.yihe.system.domain.NewInsomniaInterventionRecordForm;

import java.util.List;

public interface NewInsomniaInterventionRecordFormService {
    int insetNew(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm);

    int updateNew(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm);

    NewInsomniaInterventionRecordForm selectById(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm);

    List<NewInsomniaInterventionRecordForm> selectByPage(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm);

    List<NewInsomniaInterventionRecordForm> selectAll();

    int selectByPageCount(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm);
}
