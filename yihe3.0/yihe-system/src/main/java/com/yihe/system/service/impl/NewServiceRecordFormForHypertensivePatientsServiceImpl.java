package com.yihe.system.service.impl;

import com.yihe.system.domain.NewServiceRecordFormForHypertensivePatients;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewServiceRecordFormForHypertensivePatientsMapper;
import com.yihe.system.service.NewServiceRecordFormForHypertensivePatientsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewServiceRecordFormForHypertensivePatientsServiceImpl implements NewServiceRecordFormForHypertensivePatientsService {
    @Resource
    NewServiceRecordFormForHypertensivePatientsMapper newServiceRecordFormForHypertensivePatientsMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;
    @Override
    public int insetNew(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newServiceRecordFormForHypertensivePatients.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newServiceRecordFormForHypertensivePatientsMapper.insertSelective(newServiceRecordFormForHypertensivePatients);
    }

    @Override
    public int updateNew(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients) {
        return newServiceRecordFormForHypertensivePatientsMapper.updateByPrimaryKeySelective(newServiceRecordFormForHypertensivePatients);
    }

    @Override
    public NewServiceRecordFormForHypertensivePatients selectById(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients) {
        return newServiceRecordFormForHypertensivePatientsMapper.selectByPrimaryKey(newServiceRecordFormForHypertensivePatients.getId());
    }

    @Override
    public List<NewServiceRecordFormForHypertensivePatients> selectByPage(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients) {
        return newServiceRecordFormForHypertensivePatientsMapper.selectByPage(newServiceRecordFormForHypertensivePatients);
    }

    @Override
    public List<NewServiceRecordFormForHypertensivePatients> selectAll() {
        return newServiceRecordFormForHypertensivePatientsMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewServiceRecordFormForHypertensivePatients newServiceRecordFormForHypertensivePatients) {
        return newServiceRecordFormForHypertensivePatientsMapper.selectByPageCount(newServiceRecordFormForHypertensivePatients);
    }
}
