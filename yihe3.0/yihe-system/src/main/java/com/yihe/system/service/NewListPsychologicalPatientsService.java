package com.yihe.system.service;

import com.yihe.system.domain.NewListPsychologicalPatients;
import com.yihe.system.domain.NewObesityPatientServiceRecord;

import java.util.List;

public interface NewListPsychologicalPatientsService {
    int insetNew(NewListPsychologicalPatients newListPsychologicalPatients);

    int updateNew(NewListPsychologicalPatients newListPsychologicalPatients);

    NewListPsychologicalPatients selectById(NewListPsychologicalPatients newListPsychologicalPatients);

    List<NewListPsychologicalPatients> selectByPage(NewListPsychologicalPatients newListPsychologicalPatients);

    int selectByPageCount(NewListPsychologicalPatients newListPsychologicalPatients);
}
