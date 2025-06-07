package com.yihe.system.service;

import com.yihe.system.domain.NewServiceRecordFormForHypertensivePatients;
import com.yihe.system.domain.NewType2DiabetesPatientServiceRecord;

import java.util.List;

public interface NewType2DiabetesPatientServiceRecordService {
    int insetNew(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord);

    int updateNew(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord);

    NewType2DiabetesPatientServiceRecord selectById(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord);

    List<NewType2DiabetesPatientServiceRecord> selectByPage(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord);

    List<NewType2DiabetesPatientServiceRecord> selectAll();

    int selectByPageCount(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord);
}
