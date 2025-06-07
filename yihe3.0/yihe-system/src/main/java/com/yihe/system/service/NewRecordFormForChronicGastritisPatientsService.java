package com.yihe.system.service;

import com.yihe.system.domain.NewDermatosisPatientServiceRecordForm;
import com.yihe.system.domain.NewRecordFormForChronicGastritisPatients;

import java.util.List;

public interface NewRecordFormForChronicGastritisPatientsService {
    int insetNew(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients);

    int updateNew(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients);

    NewRecordFormForChronicGastritisPatients selectById(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients);

    List<NewRecordFormForChronicGastritisPatients> selectByPage(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients);

    List<NewRecordFormForChronicGastritisPatients> selectAll();

    int selectByPageCount(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients);
}
