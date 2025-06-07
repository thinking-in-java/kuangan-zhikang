package com.yihe.system.service.impl;

import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.newDepressionInterventionRecordForm;
import com.yihe.system.mapper.newDepressionInterventionRecordFormMapper;
import com.yihe.system.service.newDepressionInterventionRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class newDepressionInterventionRecordFormServiceImpl implements newDepressionInterventionRecordFormService {
    @Resource
    newDepressionInterventionRecordFormMapper newDepressionInterventionRecordFormMapper;
    @Override
    public int insetNew(newDepressionInterventionRecordForm newDepressionInterventionRecordForm) {
        return newDepressionInterventionRecordFormMapper.insertSelective(newDepressionInterventionRecordForm);
    }

    @Override
    public int updateNew(newDepressionInterventionRecordForm newDepressionInterventionRecordForm) {
        return newDepressionInterventionRecordFormMapper.updateByPrimaryKeySelective(newDepressionInterventionRecordForm);
    }

    @Override
    public newDepressionInterventionRecordForm selectById(newDepressionInterventionRecordForm newDepressionInterventionRecordForm) {
        return newDepressionInterventionRecordFormMapper.selectByPrimaryKey(newDepressionInterventionRecordForm.getId());
    }

    @Override
    public List<newDepressionInterventionRecordForm> selectByPage(newDepressionInterventionRecordForm newDepressionInterventionRecordForm) {
        return newDepressionInterventionRecordFormMapper.selectByPage(newDepressionInterventionRecordForm);
    }

    @Override
    public int selectByPageCount(newDepressionInterventionRecordForm newDepressionInterventionRecordForm) {
        return newDepressionInterventionRecordFormMapper.selectByPageCount(newDepressionInterventionRecordForm);
    }
}
