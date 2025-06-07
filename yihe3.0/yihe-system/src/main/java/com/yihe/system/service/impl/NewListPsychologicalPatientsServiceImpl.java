package com.yihe.system.service.impl;

import com.yihe.system.domain.NewListPsychologicalPatients;
import com.yihe.system.mapper.NewListPsychologicalPatientsMapper;
import com.yihe.system.service.NewListPsychologicalPatientsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewListPsychologicalPatientsServiceImpl implements NewListPsychologicalPatientsService {
    @Resource
    NewListPsychologicalPatientsMapper newListPsychologicalPatientsMapper;

    @Override
    public int insetNew(NewListPsychologicalPatients newListPsychologicalPatients) {
        return newListPsychologicalPatientsMapper.insertSelective(newListPsychologicalPatients);
    }

    @Override
    public int updateNew(NewListPsychologicalPatients newListPsychologicalPatients) {
        return newListPsychologicalPatientsMapper.updateByPrimaryKeySelective(newListPsychologicalPatients);
    }

    @Override
    public NewListPsychologicalPatients selectById(NewListPsychologicalPatients newListPsychologicalPatients) {
        return newListPsychologicalPatientsMapper.selectByPrimaryKey(newListPsychologicalPatients.getId());
    }

    @Override
    public List<NewListPsychologicalPatients> selectByPage(NewListPsychologicalPatients newListPsychologicalPatients) {
        return newListPsychologicalPatientsMapper.selectByPage(newListPsychologicalPatients);
    }

    @Override
    public int selectByPageCount(NewListPsychologicalPatients newListPsychologicalPatients) {
        return newListPsychologicalPatientsMapper.selectByPageCount(newListPsychologicalPatients);
    }
}
