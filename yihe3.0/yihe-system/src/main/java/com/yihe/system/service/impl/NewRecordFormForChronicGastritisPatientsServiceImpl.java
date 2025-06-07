package com.yihe.system.service.impl;

import com.yihe.system.domain.NewDermatosisPatientServiceRecordForm;
import com.yihe.system.domain.NewRecordFormForChronicGastritisPatients;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewRecordFormForChronicGastritisPatientsMapper;
import com.yihe.system.service.NewRecordFormForChronicGastritisPatientsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewRecordFormForChronicGastritisPatientsServiceImpl implements NewRecordFormForChronicGastritisPatientsService {
    @Resource
    NewRecordFormForChronicGastritisPatientsMapper recordFormForChronicGastritisPatientsMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;
    @Override
    public int insetNew(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newRecordFormForChronicGastritisPatients.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return recordFormForChronicGastritisPatientsMapper.insertSelective(newRecordFormForChronicGastritisPatients);
    }

    @Override
    public int updateNew(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients) {
        return recordFormForChronicGastritisPatientsMapper.updateByPrimaryKeySelective(newRecordFormForChronicGastritisPatients);
    }

    @Override
    public NewRecordFormForChronicGastritisPatients selectById(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients) {
        return recordFormForChronicGastritisPatientsMapper.selectByPrimaryKey(newRecordFormForChronicGastritisPatients.getId());
    }

    @Override
    public List<NewRecordFormForChronicGastritisPatients> selectByPage(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients) {
        return recordFormForChronicGastritisPatientsMapper.selectByPage(newRecordFormForChronicGastritisPatients);
    }

    @Override
    public List<NewRecordFormForChronicGastritisPatients> selectAll() {
        return recordFormForChronicGastritisPatientsMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewRecordFormForChronicGastritisPatients newRecordFormForChronicGastritisPatients) {
        return recordFormForChronicGastritisPatientsMapper.selectByPageCount(newRecordFormForChronicGastritisPatients);
    }
}
