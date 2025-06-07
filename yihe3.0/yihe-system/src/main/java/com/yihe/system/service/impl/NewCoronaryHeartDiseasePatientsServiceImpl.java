package com.yihe.system.service.impl;

import com.yihe.system.domain.NewCoronaryHeartDiseasePatients;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewCoronaryHeartDiseasePatientsMapper;
import com.yihe.system.service.NewCoronaryHeartDiseasePatientsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewCoronaryHeartDiseasePatientsServiceImpl implements NewCoronaryHeartDiseasePatientsService {
    @Resource
    NewCoronaryHeartDiseasePatientsMapper newCoronaryHeartDiseasePatientsMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;

    @Override
    public int insetNew(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newCoronaryHeartDiseasePatients.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newCoronaryHeartDiseasePatientsMapper.insertSelective(newCoronaryHeartDiseasePatients);
    }

    @Override
    public int updateNew(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients) {
        return newCoronaryHeartDiseasePatientsMapper.updateByPrimaryKeySelective(newCoronaryHeartDiseasePatients);
    }

    @Override
    public NewCoronaryHeartDiseasePatients selectById(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients) {
        return newCoronaryHeartDiseasePatientsMapper.selectByPrimaryKey(newCoronaryHeartDiseasePatients.getId());
    }

    @Override
    public List<NewCoronaryHeartDiseasePatients> selectByPage(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients) {
        return newCoronaryHeartDiseasePatientsMapper.selectByPage(newCoronaryHeartDiseasePatients);
    }

    @Override
    public List<NewCoronaryHeartDiseasePatients> selectAll() {
        return newCoronaryHeartDiseasePatientsMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewCoronaryHeartDiseasePatients newCoronaryHeartDiseasePatients) {
        return newCoronaryHeartDiseasePatientsMapper.selectByPageCount(newCoronaryHeartDiseasePatients);
    }
}
