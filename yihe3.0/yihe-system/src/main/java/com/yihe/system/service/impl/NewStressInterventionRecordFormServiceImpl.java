package com.yihe.system.service.impl;

import com.yihe.system.domain.NewStressInterventionRecordForm;
import com.yihe.system.mapper.NewStressInterventionRecordFormMapper;
import com.yihe.system.service.NewStressInterventionRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewStressInterventionRecordFormServiceImpl implements NewStressInterventionRecordFormService {
    @Resource
    NewStressInterventionRecordFormMapper newStressInterventionRecordFormMapper;


    @Override
    public int insetNew(NewStressInterventionRecordForm newStressInterventionRecordForm) {
        return newStressInterventionRecordFormMapper.insertSelective(newStressInterventionRecordForm);
    }

    @Override
    public int updateNew(NewStressInterventionRecordForm newStressInterventionRecordForm) {
        return newStressInterventionRecordFormMapper.updateByPrimaryKey(newStressInterventionRecordForm);
    }

    @Override
    public NewStressInterventionRecordForm selectById(NewStressInterventionRecordForm newStressInterventionRecordForm) {
        return newStressInterventionRecordFormMapper.selectByPrimaryKey(newStressInterventionRecordForm.getId());
    }

    @Override
    public List<NewStressInterventionRecordForm> selectByPage(NewStressInterventionRecordForm newStressInterventionRecordForm) {
        return newStressInterventionRecordFormMapper.selectByPage(newStressInterventionRecordForm);
    }

    @Override
    public int selectByPageCount(NewStressInterventionRecordForm newStressInterventionRecordForm) {
        return newStressInterventionRecordFormMapper.selectByPageCount(newStressInterventionRecordForm);
    }
}
