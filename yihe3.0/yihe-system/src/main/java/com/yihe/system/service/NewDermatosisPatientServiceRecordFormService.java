package com.yihe.system.service;

import com.yihe.system.domain.NewDermatosisPatientServiceRecordForm;

import java.util.List;

public interface NewDermatosisPatientServiceRecordFormService {
    int insetNew(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm);

    int updateNew(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm);

    NewDermatosisPatientServiceRecordForm selectById(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm);

    List<NewDermatosisPatientServiceRecordForm> selectByPage(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm);

    List<NewDermatosisPatientServiceRecordForm> selectAll();

    int selectByPageCount(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm);
}
