package com.yihe.system.service.impl;

import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.NewCoronaryHeartDiseasePatients;
import com.yihe.system.mapper.NewAnxietyDisorderInterventionRecordFormMapper;
import com.yihe.system.service.NewAnxietyDisorderInterventionRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewAnxietyDisorderInterventionRecordFormServiceImpl implements NewAnxietyDisorderInterventionRecordFormService {
    @Resource
    NewAnxietyDisorderInterventionRecordFormMapper newAnxietyDisorderInterventionRecordFormMapper;


    @Override
    public int insetNew(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm) {
        return newAnxietyDisorderInterventionRecordFormMapper.insertSelective(newAnxietyDisorderInterventionRecordForm);
    }

    @Override
    public int updateNew(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm) {
        return newAnxietyDisorderInterventionRecordFormMapper.updateByPrimaryKeySelective(newAnxietyDisorderInterventionRecordForm);
    }

    @Override
    public NewAnxietyDisorderInterventionRecordForm selectById(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm) {
        return newAnxietyDisorderInterventionRecordFormMapper.selectByPrimaryKey(newAnxietyDisorderInterventionRecordForm.getId());
    }

    @Override
    public List<NewAnxietyDisorderInterventionRecordForm> selectByPage(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm) {
        return newAnxietyDisorderInterventionRecordFormMapper.selectByPage(newAnxietyDisorderInterventionRecordForm);
    }

    @Override
    public int selectByPageCount(NewAnxietyDisorderInterventionRecordForm newAnxietyDisorderInterventionRecordForm) {
        return newAnxietyDisorderInterventionRecordFormMapper.selectByPageCount(newAnxietyDisorderInterventionRecordForm);
    }
}
