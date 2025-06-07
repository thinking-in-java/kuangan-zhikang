package com.yihe.system.service.impl;

import com.yihe.system.domain.NewObesityPatientServiceRecord;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewObesityPatientServiceRecordMapper;
import com.yihe.system.service.NewObesityPatientServiceRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewObesityPatientServiceRecordServiceImpl implements NewObesityPatientServiceRecordService {
    @Resource
    NewObesityPatientServiceRecordMapper newObesityPatientServiceRecordMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;
    @Override
    public int insetNew(NewObesityPatientServiceRecord newObesityPatientServiceRecord) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newObesityPatientServiceRecord.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newObesityPatientServiceRecordMapper.insertSelective(newObesityPatientServiceRecord);
    }

    @Override
    public int updateNew(NewObesityPatientServiceRecord newObesityPatientServiceRecord) {
        return newObesityPatientServiceRecordMapper.updateByPrimaryKeySelective(newObesityPatientServiceRecord);
    }

    @Override
    public NewObesityPatientServiceRecord selectById(NewObesityPatientServiceRecord newObesityPatientServiceRecord) {
        return newObesityPatientServiceRecordMapper.selectByPrimaryKey(newObesityPatientServiceRecord.getId());
    }

    @Override
    public List<NewObesityPatientServiceRecord> selectByPage(NewObesityPatientServiceRecord newObesityPatientServiceRecord) {
        return newObesityPatientServiceRecordMapper.selectByPage(newObesityPatientServiceRecord);
    }

    @Override
    public List<NewObesityPatientServiceRecord> selectAll() {
        return newObesityPatientServiceRecordMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewObesityPatientServiceRecord newObesityPatientServiceRecord) {
        return newObesityPatientServiceRecordMapper.selectByPageCount(newObesityPatientServiceRecord);
    }
}
