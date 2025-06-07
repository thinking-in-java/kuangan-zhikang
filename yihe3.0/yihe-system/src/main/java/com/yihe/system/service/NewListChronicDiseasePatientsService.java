package com.yihe.system.service;

import com.yihe.system.domain.NewListChronicDiseasePatients;
import com.yihe.system.domain.NewRisk;

import java.util.List;

public interface NewListChronicDiseasePatientsService {
    int insetNew(NewListChronicDiseasePatients newListChronicDiseasePatients);

    int updateNew(NewListChronicDiseasePatients newListChronicDiseasePatients);

    NewListChronicDiseasePatients selectById(NewListChronicDiseasePatients newListChronicDiseasePatients);

    List<NewListChronicDiseasePatients> selectByPage(NewListChronicDiseasePatients newListChronicDiseasePatients);

    int selectByPageCount(NewListChronicDiseasePatients newListChronicDiseasePatients);
}
