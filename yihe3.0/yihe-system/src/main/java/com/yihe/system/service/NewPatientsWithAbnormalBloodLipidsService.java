package com.yihe.system.service;

import com.yihe.system.domain.NewPatientsWithAbnormalBloodLipids;
import com.yihe.system.domain.NewServiceRecordFormForHypertensivePatients;

import java.util.List;

public interface NewPatientsWithAbnormalBloodLipidsService {
    int insetNew(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids);

    int updateNew(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids);

    NewPatientsWithAbnormalBloodLipids selectById(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids);

    List<NewPatientsWithAbnormalBloodLipids> selectByPage(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids);

    List<NewPatientsWithAbnormalBloodLipids> selectAll();

    int selectByPageCount(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids);
}
