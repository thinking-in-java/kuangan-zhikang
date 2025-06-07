package com.yihe.system.service;

import com.yihe.system.domain.NewRisk;
import com.yihe.system.domain.NewServiceRecordFormForHypertensivePatients;

import java.util.List;

public interface NewServiceRecordFormForHypertensivePatientsService {
    int insetNew(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients);

    int updateNew(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients);

    NewServiceRecordFormForHypertensivePatients selectById(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients);

    List<NewServiceRecordFormForHypertensivePatients> selectByPage(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients);

    List<NewServiceRecordFormForHypertensivePatients> selectAll();

    int selectByPageCount(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients);
}
