package com.yihe.system.service.impl;

import com.yihe.system.domain.NewRecordFormForStrokePatients;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewRecordFormForStrokePatientsMapper;
import com.yihe.system.service.NewRecordFormForStrokePatientsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewRecordFormForStrokePatientsServiceImpl implements NewRecordFormForStrokePatientsService {
    @Resource
    NewRecordFormForStrokePatientsMapper newRecordFormForStrokePatientsMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;

    @Override
    public int insetNew(NewRecordFormForStrokePatients newRecordFormForStrokePatients) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newRecordFormForStrokePatients.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newRecordFormForStrokePatientsMapper.insertSelective(newRecordFormForStrokePatients);
    }

    @Override
    public int updateNew(NewRecordFormForStrokePatients newRecordFormForStrokePatients) {
        return newRecordFormForStrokePatientsMapper.updateByPrimaryKeySelective(newRecordFormForStrokePatients);
    }

    @Override
    public NewRecordFormForStrokePatients selectById(NewRecordFormForStrokePatients newRecordFormForStrokePatients) {
        return newRecordFormForStrokePatientsMapper.selectByPrimaryKey(newRecordFormForStrokePatients.getId());
    }

    @Override
    public List<NewRecordFormForStrokePatients> selectByPage(NewRecordFormForStrokePatients newRecordFormForStrokePatients) {
        return newRecordFormForStrokePatientsMapper.selectByPage(newRecordFormForStrokePatients);
    }

    @Override
    public List<NewRecordFormForStrokePatients> selectAll() {
        return newRecordFormForStrokePatientsMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewRecordFormForStrokePatients newRecordFormForStrokePatients) {
        return newRecordFormForStrokePatientsMapper.selectByPageCount(newRecordFormForStrokePatients);
    }
}
