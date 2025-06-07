package com.yihe.system.service;

import com.yihe.system.domain.NewProstatitisPatientServiceRecordForm;
import com.yihe.system.domain.NewRecordFormForChronicGastritisPatients;

import java.util.List;

public interface NewProstatitisPatientServiceRecordFormService {
    int insetNew(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm);

    int updateNew(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm);

    NewProstatitisPatientServiceRecordForm selectById(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm);

    List<NewProstatitisPatientServiceRecordForm> selectByPage(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm);

    List<NewProstatitisPatientServiceRecordForm> selectAll();

    int selectByPageCount(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm);
}
