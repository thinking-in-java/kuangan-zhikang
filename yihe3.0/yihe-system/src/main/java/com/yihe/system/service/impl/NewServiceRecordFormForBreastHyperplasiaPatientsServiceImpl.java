package com.yihe.system.service.impl;

import com.yihe.system.domain.NewRecordFormForChronicGastritisPatients;
import com.yihe.system.domain.NewServiceRecordFormForBreastHyperplasiaPatients;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewServiceRecordFormForBreastHyperplasiaPatientsMapper;
import com.yihe.system.service.NewServiceRecordFormForBreastHyperplasiaPatientsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewServiceRecordFormForBreastHyperplasiaPatientsServiceImpl implements NewServiceRecordFormForBreastHyperplasiaPatientsService {
    @Resource
    NewServiceRecordFormForBreastHyperplasiaPatientsMapper newServiceRecordFormForBreastHyperplasiaPatientsMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;
    @Override
    public int insetNew(NewServiceRecordFormForBreastHyperplasiaPatients newServiceRecordFormForBreastHyperplasiaPatients) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newServiceRecordFormForBreastHyperplasiaPatients.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newServiceRecordFormForBreastHyperplasiaPatientsMapper.insertSelective(newServiceRecordFormForBreastHyperplasiaPatients);
    }

    @Override
    public int updateNew(NewServiceRecordFormForBreastHyperplasiaPatients newServiceRecordFormForBreastHyperplasiaPatients) {
        return newServiceRecordFormForBreastHyperplasiaPatientsMapper.updateByPrimaryKeySelective(newServiceRecordFormForBreastHyperplasiaPatients);
    }

    @Override
    public NewServiceRecordFormForBreastHyperplasiaPatients selectById(NewServiceRecordFormForBreastHyperplasiaPatients newServiceRecordFormForBreastHyperplasiaPatients) {
        return newServiceRecordFormForBreastHyperplasiaPatientsMapper.selectByPrimaryKey(newServiceRecordFormForBreastHyperplasiaPatients.getId());
    }

    @Override
    public List<NewServiceRecordFormForBreastHyperplasiaPatients> selectByPage(NewServiceRecordFormForBreastHyperplasiaPatients newServiceRecordFormForBreastHyperplasiaPatients) {
        return newServiceRecordFormForBreastHyperplasiaPatientsMapper.selectByPage(newServiceRecordFormForBreastHyperplasiaPatients);
    }

    @Override
    public List<NewServiceRecordFormForBreastHyperplasiaPatients> selectAll() {
        return newServiceRecordFormForBreastHyperplasiaPatientsMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewServiceRecordFormForBreastHyperplasiaPatients newServiceRecordFormForBreastHyperplasiaPatients) {
        return newServiceRecordFormForBreastHyperplasiaPatientsMapper.selectByPageCount(newServiceRecordFormForBreastHyperplasiaPatients);
    }
}
