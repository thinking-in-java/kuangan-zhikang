package com.yihe.system.mapper;


import com.yihe.system.domain.NewPsychologicalAssessmentReport;

import java.util.List;

public interface NewPsychologicalAssessmentReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewPsychologicalAssessmentReport row);

    int insertSelective(NewPsychologicalAssessmentReport row);

    NewPsychologicalAssessmentReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewPsychologicalAssessmentReport row);

    int updateByPrimaryKey(NewPsychologicalAssessmentReport row);

    List<NewPsychologicalAssessmentReport> selectByPage(NewPsychologicalAssessmentReport row);

    int selectByPageCount(NewPsychologicalAssessmentReport row);
}