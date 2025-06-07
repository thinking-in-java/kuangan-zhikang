package com.yihe.system.service;

import com.yihe.system.domain.NewServiceRecordFormForHypertensivePatients;
import com.yihe.system.domain.NewServiceRecordFormForOsteoarthritisPatients;

import java.util.List;

public interface NewServiceRecordFormForOsteoarthritisPatientsService {
    int insetNew(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients);

    int updateNew(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients);

    NewServiceRecordFormForOsteoarthritisPatients selectById(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients);

    List<NewServiceRecordFormForOsteoarthritisPatients> selectByPage(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients);

    List<NewServiceRecordFormForOsteoarthritisPatients> selectAll();

    int selectByPageCount(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients);
}
