package com.yihe.system.service.impl;

import com.yihe.system.domain.NewProstatitisPatientServiceRecordForm;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewProstatitisPatientServiceRecordFormMapper;
import com.yihe.system.service.NewProstatitisPatientServiceRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewProstatitisPatientServiceRecordFormServiceImpl implements NewProstatitisPatientServiceRecordFormService {
    @Resource
    NewProstatitisPatientServiceRecordFormMapper newProstatitisPatientServiceRecordFormMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;

    @Override
    public int insetNew(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newProstatitisPatientServiceRecordForm.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newProstatitisPatientServiceRecordFormMapper.insertSelective(newProstatitisPatientServiceRecordForm);
    }

    @Override
    public int updateNew(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm) {
        return newProstatitisPatientServiceRecordFormMapper.updateByPrimaryKeySelective(newProstatitisPatientServiceRecordForm);
    }

    @Override
    public NewProstatitisPatientServiceRecordForm selectById(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm) {
        return newProstatitisPatientServiceRecordFormMapper.selectByPrimaryKey(newProstatitisPatientServiceRecordForm.getId());
    }

    @Override
    public List<NewProstatitisPatientServiceRecordForm> selectByPage(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm) {
        return newProstatitisPatientServiceRecordFormMapper.selectByPage(newProstatitisPatientServiceRecordForm);
    }

    @Override
    public List<NewProstatitisPatientServiceRecordForm> selectAll() {
        return newProstatitisPatientServiceRecordFormMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewProstatitisPatientServiceRecordForm newProstatitisPatientServiceRecordForm) {
        return newProstatitisPatientServiceRecordFormMapper.selectByPageCount(newProstatitisPatientServiceRecordForm);
    }
}
