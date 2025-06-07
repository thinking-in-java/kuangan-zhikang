package com.yihe.system.service.impl;

import com.yihe.system.domain.NewType2DiabetesPatientServiceRecord;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewType2DiabetesPatientServiceRecordMapper;
import com.yihe.system.service.NewType2DiabetesPatientServiceRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewType2DiabetesPatientServiceRecordServiceImpl implements NewType2DiabetesPatientServiceRecordService {
    @Resource
    NewType2DiabetesPatientServiceRecordMapper newType2DiabetesPatientServiceRecordMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;
    @Override
    public int insetNew(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newType2DiabetesPatientServiceRecord.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newType2DiabetesPatientServiceRecordMapper.insertSelective(newType2DiabetesPatientServiceRecord);
    }

    @Override
    public int updateNew(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord) {
        return newType2DiabetesPatientServiceRecordMapper.updateByPrimaryKeySelective(newType2DiabetesPatientServiceRecord);
    }

    @Override
    public NewType2DiabetesPatientServiceRecord selectById(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord) {
        return newType2DiabetesPatientServiceRecordMapper.selectByPrimaryKey(newType2DiabetesPatientServiceRecord.getId());
    }

    @Override
    public List<NewType2DiabetesPatientServiceRecord> selectByPage(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord) {
        return newType2DiabetesPatientServiceRecordMapper.selectByPage(newType2DiabetesPatientServiceRecord);
    }

    @Override
    public List<NewType2DiabetesPatientServiceRecord> selectAll() {
        return newType2DiabetesPatientServiceRecordMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewType2DiabetesPatientServiceRecord newType2DiabetesPatientServiceRecord) {
        return newType2DiabetesPatientServiceRecordMapper.selectByPageCount(newType2DiabetesPatientServiceRecord);
    }
}
