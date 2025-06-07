package com.yihe.system.service.impl;

import com.yihe.system.domain.NewInsomniaInterventionRecordForm;
import com.yihe.system.mapper.NewInsomniaInterventionRecordFormMapper;
import com.yihe.system.service.NewInsomniaInterventionRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewInsomniaInterventionRecordFormServiceImpl implements NewInsomniaInterventionRecordFormService {
    @Resource
    NewInsomniaInterventionRecordFormMapper newInsomniaInterventionRecordFormMapper;


    @Override
    public int insetNew(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm) {
        return newInsomniaInterventionRecordFormMapper.insertSelective(newInsomniaInterventionRecordForm);
    }

    @Override
    public int updateNew(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm) {
        return newInsomniaInterventionRecordFormMapper.updateByPrimaryKeySelective(newInsomniaInterventionRecordForm);
    }

    @Override
    public NewInsomniaInterventionRecordForm selectById(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm) {
        return newInsomniaInterventionRecordFormMapper.selectByPrimaryKey(newInsomniaInterventionRecordForm.getId());
    }

    @Override
    public List<NewInsomniaInterventionRecordForm> selectByPage(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm) {
        return newInsomniaInterventionRecordFormMapper.selectByPage(newInsomniaInterventionRecordForm);
    }

    @Override
    public List<NewInsomniaInterventionRecordForm> selectAll() {
        return newInsomniaInterventionRecordFormMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewInsomniaInterventionRecordForm newInsomniaInterventionRecordForm) {
        return newInsomniaInterventionRecordFormMapper.selectByPageCount(newInsomniaInterventionRecordForm);
    }
}
