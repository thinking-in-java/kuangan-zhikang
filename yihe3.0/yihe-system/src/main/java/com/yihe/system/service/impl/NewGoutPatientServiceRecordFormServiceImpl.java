package com.yihe.system.service.impl;

import com.yihe.system.domain.NewGoutPatientServiceRecordForm;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewGoutPatientServiceRecordFormMapper;
import com.yihe.system.service.NewGoutPatientServiceRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewGoutPatientServiceRecordFormServiceImpl implements NewGoutPatientServiceRecordFormService {
    @Resource
    NewGoutPatientServiceRecordFormMapper newGoutPatientServiceRecordFormMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;
    @Override
    public int insetNew(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newGoutPatientServiceRecordForm.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newGoutPatientServiceRecordFormMapper.insertSelective(newGoutPatientServiceRecordForm);
    }

    @Override
    public int updateNew(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm) {
        return newGoutPatientServiceRecordFormMapper.updateByPrimaryKeySelective(newGoutPatientServiceRecordForm);
    }

    @Override
    public NewGoutPatientServiceRecordForm selectById(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm) {
        return newGoutPatientServiceRecordFormMapper.selectByPrimaryKey(newGoutPatientServiceRecordForm.getId());
    }

    @Override
    public List<NewGoutPatientServiceRecordForm> selectByPage(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm) {
        return newGoutPatientServiceRecordFormMapper.selectByPage(newGoutPatientServiceRecordForm);
    }

    @Override
    public List<NewGoutPatientServiceRecordForm> selectAll() {
        return newGoutPatientServiceRecordFormMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewGoutPatientServiceRecordForm newGoutPatientServiceRecordForm) {
        return newGoutPatientServiceRecordFormMapper.selectByPageCount(newGoutPatientServiceRecordForm);
    }
}
