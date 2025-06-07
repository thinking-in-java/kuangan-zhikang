package com.yihe.system.service;

import com.yihe.system.domain.NewServiceFormForOsteoporosisPatients;
import com.yihe.system.domain.NewServiceRecordFormForOsteoarthritisPatients;

import java.util.List;

public interface NewServiceFormForOsteoporosisPatientsService {
    int insetNew(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients);

    int updateNew(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients);

    NewServiceFormForOsteoporosisPatients selectById(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients);

    List<NewServiceFormForOsteoporosisPatients> selectByPage(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients);

    List<NewServiceFormForOsteoporosisPatients> selectAll();

    int selectByPageCount(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients);
}
