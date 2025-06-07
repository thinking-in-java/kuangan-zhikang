package com.yihe.system.service;

import com.yihe.system.domain.NewCoronaryHeartDiseasePatients;
import com.yihe.system.domain.NewGoutPatientServiceRecordForm;

import java.util.List;

public interface NewCoronaryHeartDiseasePatientsService {
    int insetNew(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients);

    int updateNew(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients);

    NewCoronaryHeartDiseasePatients selectById(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients);

    List<NewCoronaryHeartDiseasePatients> selectByPage(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients);

    List<NewCoronaryHeartDiseasePatients> selectAll();

    int selectByPageCount(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients);
}
