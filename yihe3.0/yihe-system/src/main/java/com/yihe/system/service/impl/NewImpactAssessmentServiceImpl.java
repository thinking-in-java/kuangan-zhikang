package com.yihe.system.service.impl;

import com.yihe.system.domain.NewImpactAssessment;
import com.yihe.system.mapper.NewImpactAssessmentMapper;
import com.yihe.system.service.NewImpactAssessmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewImpactAssessmentServiceImpl implements NewImpactAssessmentService {
    @Resource
    NewImpactAssessmentMapper newImpactAssessmentMapper;
    @Override
    public int insetNew(NewImpactAssessment newImpactAssessment) {
        return newImpactAssessmentMapper.insertSelective(newImpactAssessment);
    }

    @Override
    public int updateNew(NewImpactAssessment newImpactAssessment) {
        return newImpactAssessmentMapper.updateByPrimaryKeySelective(newImpactAssessment);
    }

    @Override
    public NewImpactAssessment selectById(NewImpactAssessment newImpactAssessment) {
        return newImpactAssessmentMapper.selectByPrimaryKey(newImpactAssessment.getId());
    }

    @Override
    public List<NewImpactAssessment> selectByPage(NewImpactAssessment newImpactAssessment) {
        return newImpactAssessmentMapper.selectByPage(newImpactAssessment);
    }

    @Override
    public int selectByPageCount(NewImpactAssessment newImpactAssessment) {
        return newImpactAssessmentMapper.selectByPageCount(newImpactAssessment);
    }
}
