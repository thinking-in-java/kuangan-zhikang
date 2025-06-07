package com.yihe.system.service.impl;

import com.yihe.system.domain.NewPatientsWithAbnormalBloodLipids;
import com.yihe.system.domain.NewServiceRecordFormForHypertensivePatients;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewPatientsWithAbnormalBloodLipidsMapper;
import com.yihe.system.service.NewPatientsWithAbnormalBloodLipidsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewPatientsWithAbnormalBloodLipidsServiceImpl implements NewPatientsWithAbnormalBloodLipidsService {
    @Resource
    NewPatientsWithAbnormalBloodLipidsMapper newPatientsWithAbnormalBloodLipidsMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;


    @Override
    public int insetNew(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newPatientsWithAbnormalBloodLipids.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newPatientsWithAbnormalBloodLipidsMapper.insertSelective(newPatientsWithAbnormalBloodLipids);
    }

    @Override
    public int updateNew(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids) {
        return newPatientsWithAbnormalBloodLipidsMapper.updateByPrimaryKeySelective(newPatientsWithAbnormalBloodLipids);
    }

    @Override
    public NewPatientsWithAbnormalBloodLipids selectById(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids) {
        return newPatientsWithAbnormalBloodLipidsMapper.selectByPrimaryKey(newPatientsWithAbnormalBloodLipids.getId());
    }

    @Override
    public List<NewPatientsWithAbnormalBloodLipids> selectByPage(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids) {
        return newPatientsWithAbnormalBloodLipidsMapper.selectByPage(newPatientsWithAbnormalBloodLipids);
    }

    @Override
    public List<NewPatientsWithAbnormalBloodLipids> selectAll() {
        return newPatientsWithAbnormalBloodLipidsMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewPatientsWithAbnormalBloodLipids newPatientsWithAbnormalBloodLipids) {
        return newPatientsWithAbnormalBloodLipidsMapper.selectByPageCount(newPatientsWithAbnormalBloodLipids);
    }
}
