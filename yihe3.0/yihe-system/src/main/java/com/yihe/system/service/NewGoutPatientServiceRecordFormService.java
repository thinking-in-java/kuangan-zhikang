package com.yihe.system.service;

import com.yihe.system.domain.NewGoutPatientServiceRecordForm;
import com.yihe.system.domain.NewServiceRecordFormForHypertensivePatients;

import java.util.List;

public interface NewGoutPatientServiceRecordFormService {
    int insetNew(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm);

    int updateNew(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm);

    NewGoutPatientServiceRecordForm selectById(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm);

    List<NewGoutPatientServiceRecordForm> selectByPage(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm);

    List<NewGoutPatientServiceRecordForm> selectAll();

    int selectByPageCount(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm);
}
