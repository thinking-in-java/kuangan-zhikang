package com.yihe.system.service.impl;

import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.NewObsessiveCompulsiveDisorderInterventionRecordForm;
import com.yihe.system.mapper.NewObsessiveCompulsiveDisorderInterventionRecordFormMapper;
import com.yihe.system.service.NewObsessiveCompulsiveDisorderInterventionRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewObsessiveCompulsiveDisorderInterventionRecordFormServiceImpl implements NewObsessiveCompulsiveDisorderInterventionRecordFormService {

    @Resource
    NewObsessiveCompulsiveDisorderInterventionRecordFormMapper newObsessiveCompulsiveDisorderInterventionRecordFormMapper;

    @Override
    public int insetNew(NewObsessiveCompulsiveDisorderInterventionRecordForm newObsessiveCompulsiveDisorderInterventionRecordForm) {
        return newObsessiveCompulsiveDisorderInterventionRecordFormMapper.insert(newObsessiveCompulsiveDisorderInterventionRecordForm);
    }

    @Override
    public int updateNew(NewObsessiveCompulsiveDisorderInterventionRecordForm newObsessiveCompulsiveDisorderInterventionRecordForm) {
        return newObsessiveCompulsiveDisorderInterventionRecordFormMapper.updateByPrimaryKeySelective(newObsessiveCompulsiveDisorderInterventionRecordForm);
    }

    @Override
    public NewObsessiveCompulsiveDisorderInterventionRecordForm selectById(NewObsessiveCompulsiveDisorderInterventionRecordForm newObsessiveCompulsiveDisorderInterventionRecordForm) {
        return newObsessiveCompulsiveDisorderInterventionRecordFormMapper.selectByPrimaryKey(newObsessiveCompulsiveDisorderInterventionRecordForm.getId());
    }

    @Override
    public List<NewObsessiveCompulsiveDisorderInterventionRecordForm> selectByPage(NewObsessiveCompulsiveDisorderInterventionRecordForm newObsessiveCompulsiveDisorderInterventionRecordForm) {
        return newObsessiveCompulsiveDisorderInterventionRecordFormMapper.selectByPage(newObsessiveCompulsiveDisorderInterventionRecordForm);
    }

    @Override
    public int selectByPageCount(NewObsessiveCompulsiveDisorderInterventionRecordForm newObsessiveCompulsiveDisorderInterventionRecordForm) {
        return newObsessiveCompulsiveDisorderInterventionRecordFormMapper.selectByPageCount(newObsessiveCompulsiveDisorderInterventionRecordForm);
    }
}
