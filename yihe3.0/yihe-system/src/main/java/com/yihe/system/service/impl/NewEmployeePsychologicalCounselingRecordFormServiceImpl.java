package com.yihe.system.service.impl;

import com.yihe.system.domain.NewEmployeePsychologicalCounselingRecordForm;
import com.yihe.system.mapper.NewEmployeePsychologicalCounselingRecordFormMapper;
import com.yihe.system.service.NewEmployeePsychologicalCounselingRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewEmployeePsychologicalCounselingRecordFormServiceImpl implements NewEmployeePsychologicalCounselingRecordFormService {
    @Resource
    NewEmployeePsychologicalCounselingRecordFormMapper newEmployeePsychologicalCounselingRecordFormMapper;

    @Override
    public int insetNew(NewEmployeePsychologicalCounselingRecordForm newEmployeePsychologicalCounselingRecordForm) {
        return newEmployeePsychologicalCounselingRecordFormMapper.insertSelective(newEmployeePsychologicalCounselingRecordForm);
    }

    @Override
    public int updateNew(NewEmployeePsychologicalCounselingRecordForm newEmployeePsychologicalCounselingRecordForm) {
        return newEmployeePsychologicalCounselingRecordFormMapper.updateByPrimaryKeySelective(newEmployeePsychologicalCounselingRecordForm);
    }

    @Override
    public NewEmployeePsychologicalCounselingRecordForm selectById(NewEmployeePsychologicalCounselingRecordForm newEmployeePsychologicalCounselingRecordForm) {
        return newEmployeePsychologicalCounselingRecordFormMapper.selectByPrimaryKey(newEmployeePsychologicalCounselingRecordForm.getId());
    }

    @Override
    public List<NewEmployeePsychologicalCounselingRecordForm> selectByPage(NewEmployeePsychologicalCounselingRecordForm newEmployeePsychologicalCounselingRecordForm) {
        return newEmployeePsychologicalCounselingRecordFormMapper.selectByPage(newEmployeePsychologicalCounselingRecordForm);
    }

    @Override
    public int selectByPageCount(NewEmployeePsychologicalCounselingRecordForm newEmployeePsychologicalCounselingRecordForm) {
        return newEmployeePsychologicalCounselingRecordFormMapper.selectByPageCount(newEmployeePsychologicalCounselingRecordForm);
    }
}
