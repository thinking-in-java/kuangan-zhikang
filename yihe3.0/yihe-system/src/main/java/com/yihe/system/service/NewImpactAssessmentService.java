package com.yihe.system.service;

import com.yihe.system.domain.NewGoutPatientServiceRecordForm;
import com.yihe.system.domain.NewImpactAssessment;

import java.util.List;

public interface NewImpactAssessmentService {
    int insetNew(NewImpactAssessment newImpactAssessment);

    int updateNew(NewImpactAssessment newImpactAssessment);

    NewImpactAssessment selectById(NewImpactAssessment newImpactAssessment);

    List<NewImpactAssessment> selectByPage(NewImpactAssessment newImpactAssessment);

    int selectByPageCount(NewImpactAssessment newImpactAssessment);
}
