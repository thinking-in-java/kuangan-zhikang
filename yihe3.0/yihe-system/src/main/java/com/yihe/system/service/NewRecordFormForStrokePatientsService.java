package com.yihe.system.service;

import com.yihe.system.domain.NewRecordFormForStrokePatients;
import com.yihe.system.domain.NewRisk;

import java.util.List;

public interface NewRecordFormForStrokePatientsService {

    int insetNew(NewRecordFormForStrokePatients newRecordFormForStrokePatients);

    int updateNew(NewRecordFormForStrokePatients newRecordFormForStrokePatients);

    NewRecordFormForStrokePatients selectById(NewRecordFormForStrokePatients newRecordFormForStrokePatients);

    List<NewRecordFormForStrokePatients> selectByPage(NewRecordFormForStrokePatients newRecordFormForStrokePatients);

    List<NewRecordFormForStrokePatients> selectAll();

    int selectByPageCount(NewRecordFormForStrokePatients newRecordFormForStrokePatients);
}
