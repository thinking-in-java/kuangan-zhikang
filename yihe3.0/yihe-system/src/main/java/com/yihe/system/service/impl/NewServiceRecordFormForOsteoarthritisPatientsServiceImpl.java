package com.yihe.system.service.impl;

import com.yihe.system.domain.NewServiceRecordFormForOsteoarthritisPatients;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewServiceRecordFormForOsteoarthritisPatientsMapper;
import com.yihe.system.service.NewServiceRecordFormForOsteoarthritisPatientsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewServiceRecordFormForOsteoarthritisPatientsServiceImpl implements NewServiceRecordFormForOsteoarthritisPatientsService {
    @Resource
    NewServiceRecordFormForOsteoarthritisPatientsMapper newServiceRecordFormForOsteoarthritisPatientsMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;

    @Override
    public int insetNew(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newServiceRecordFormForOsteoarthritisPatients.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newServiceRecordFormForOsteoarthritisPatientsMapper.insert(newServiceRecordFormForOsteoarthritisPatients);
    }

    @Override
    public int updateNew(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients) {
        return newServiceRecordFormForOsteoarthritisPatientsMapper.updateByPrimaryKeySelective(newServiceRecordFormForOsteoarthritisPatients);
    }

    @Override
    public NewServiceRecordFormForOsteoarthritisPatients selectById(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients) {
        return newServiceRecordFormForOsteoarthritisPatientsMapper.selectByPrimaryKey(newServiceRecordFormForOsteoarthritisPatients.getId());
    }

    @Override
    public List<NewServiceRecordFormForOsteoarthritisPatients> selectByPage(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients) {
        return newServiceRecordFormForOsteoarthritisPatientsMapper.selectByPage(newServiceRecordFormForOsteoarthritisPatients);
    }

    @Override
    public List<NewServiceRecordFormForOsteoarthritisPatients> selectAll() {
        return newServiceRecordFormForOsteoarthritisPatientsMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewServiceRecordFormForOsteoarthritisPatients newServiceRecordFormForOsteoarthritisPatients) {
        return newServiceRecordFormForOsteoarthritisPatientsMapper.selectByPageCount(newServiceRecordFormForOsteoarthritisPatients);
    }
}
