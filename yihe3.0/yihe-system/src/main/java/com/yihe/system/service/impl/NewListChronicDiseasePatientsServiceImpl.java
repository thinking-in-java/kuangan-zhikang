package com.yihe.system.service.impl;

import com.yihe.system.domain.NewListChronicDiseasePatients;
import com.yihe.system.domain.NewRisk;
import com.yihe.system.mapper.NewListChronicDiseasePatientsMapper;
import com.yihe.system.service.NewListChronicDiseasePatientsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewListChronicDiseasePatientsServiceImpl implements NewListChronicDiseasePatientsService {
    @Resource
    NewListChronicDiseasePatientsMapper newListChronicDiseasePatientsMapper;

    @Override
    public int insetNew(NewListChronicDiseasePatients newListChronicDiseasePatients) {
        return newListChronicDiseasePatientsMapper.insertSelective(newListChronicDiseasePatients);
    }

    @Override
    public int updateNew(NewListChronicDiseasePatients newListChronicDiseasePatients) {
        return newListChronicDiseasePatientsMapper.updateByPrimaryKeySelective(newListChronicDiseasePatients);
    }

    @Override
    public NewListChronicDiseasePatients selectById(NewListChronicDiseasePatients newListChronicDiseasePatients) {
        return newListChronicDiseasePatientsMapper.selectByPrimaryKey(newListChronicDiseasePatients.getId());
    }

    @Override
    public List<NewListChronicDiseasePatients> selectByPage(NewListChronicDiseasePatients newListChronicDiseasePatients) {

        return newListChronicDiseasePatientsMapper.selectByPage(newListChronicDiseasePatients);
    }

    @Override
    public int selectByPageCount(NewListChronicDiseasePatients newListChronicDiseasePatients) {
        return newListChronicDiseasePatientsMapper.selectByPageCount(newListChronicDiseasePatients);
    }
}
