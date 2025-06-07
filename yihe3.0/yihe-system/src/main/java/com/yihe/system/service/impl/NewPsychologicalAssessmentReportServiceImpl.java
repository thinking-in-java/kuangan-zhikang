package com.yihe.system.service.impl;

import com.yihe.system.domain.NewPsychologicalAssessmentReport;
import com.yihe.system.mapper.NewPsychologicalAssessmentReportMapper;
import com.yihe.system.service.NewPsychologicalAssessmentReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewPsychologicalAssessmentReportServiceImpl implements NewPsychologicalAssessmentReportService {
    @Resource
    NewPsychologicalAssessmentReportMapper newPsychologicalAssessmentReportMapper;


    @Override
    public int insetNew(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport) {
        return newPsychologicalAssessmentReportMapper.insertSelective(newPsychologicalAssessmentReport);
    }

    @Override
    public int updateNew(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport) {
        return newPsychologicalAssessmentReportMapper.updateByPrimaryKeySelective(newPsychologicalAssessmentReport);
    }

    @Override
    public NewPsychologicalAssessmentReport selectById(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport) {
        return newPsychologicalAssessmentReportMapper.selectByPrimaryKey(newPsychologicalAssessmentReport.getId());
    }

    @Override
    public List<NewPsychologicalAssessmentReport> selectByPage(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport) {
        return newPsychologicalAssessmentReportMapper.selectByPage(newPsychologicalAssessmentReport);
    }

    @Override
    public int selectByPageCount(NewPsychologicalAssessmentReport newPsychologicalAssessmentReport) {
        return newPsychologicalAssessmentReportMapper.selectByPageCount(newPsychologicalAssessmentReport);
    }
}
