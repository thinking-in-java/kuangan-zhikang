package com.yihe.system.service;

import com.yihe.system.domain.NewObesityPatientServiceRecord;
import com.yihe.system.domain.NewPneumoconiosisServiceRecordForm;

import java.util.List;

public interface NewObesityPatientServiceRecordService {
    int insetNew(NewObesityPatientServiceRecord newObesityPatientServiceRecord);

    int updateNew(NewObesityPatientServiceRecord newObesityPatientServiceRecord);

    NewObesityPatientServiceRecord selectById(NewObesityPatientServiceRecord newObesityPatientServiceRecord);

    List<NewObesityPatientServiceRecord> selectByPage(NewObesityPatientServiceRecord newObesityPatientServiceRecord);

    List<NewObesityPatientServiceRecord> selectAll();

    int selectByPageCount(NewObesityPatientServiceRecord newObesityPatientServiceRecord);
}
