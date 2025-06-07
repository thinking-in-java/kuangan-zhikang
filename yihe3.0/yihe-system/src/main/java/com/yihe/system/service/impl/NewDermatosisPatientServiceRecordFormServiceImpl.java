package com.yihe.system.service.impl;

import com.yihe.system.domain.NewDermatosisPatientServiceRecordForm;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewDermatosisPatientServiceRecordFormMapper;
import com.yihe.system.service.NewDermatosisPatientServiceRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewDermatosisPatientServiceRecordFormServiceImpl implements NewDermatosisPatientServiceRecordFormService {

    @Resource
    NewDermatosisPatientServiceRecordFormMapper newDermatosisPatientServiceRecordFormMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;

    @Override
    public int insetNew(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newDermatosisPatientServiceRecordForm.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newDermatosisPatientServiceRecordFormMapper.insertSelective(newDermatosisPatientServiceRecordForm);
    }

    @Override
    public int updateNew(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm) {
        return newDermatosisPatientServiceRecordFormMapper.updateByPrimaryKeySelective(newDermatosisPatientServiceRecordForm);
    }

    @Override
    public NewDermatosisPatientServiceRecordForm selectById(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm) {
        return newDermatosisPatientServiceRecordFormMapper.selectByPrimaryKey(newDermatosisPatientServiceRecordForm.getId());
    }

    @Override
    public List<NewDermatosisPatientServiceRecordForm> selectByPage(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm) {
        return newDermatosisPatientServiceRecordFormMapper.selectByPage(newDermatosisPatientServiceRecordForm);
    }

    @Override
    public List<NewDermatosisPatientServiceRecordForm> selectAll() {
        return newDermatosisPatientServiceRecordFormMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewDermatosisPatientServiceRecordForm newDermatosisPatientServiceRecordForm) {
        return newDermatosisPatientServiceRecordFormMapper.selectByPageCount(newDermatosisPatientServiceRecordForm);
    }
}
