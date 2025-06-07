package com.yihe.system.service;

import com.yihe.system.domain.NewPsychologicalAssessmentReport;

import java.util.List;

public interface NewPsychologicalAssessmentReportService {
    int insetNew(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport);

    int updateNew(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport);

    NewPsychologicalAssessmentReport selectById(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport);

    List<NewPsychologicalAssessmentReport> selectByPage(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport);

    int selectByPageCount(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport);
}
