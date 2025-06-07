package com.yihe.system.service.impl;

import com.yihe.system.domain.NewServiceFormForOsteoporosisPatients;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewServiceFormForOsteoporosisPatientsMapper;
import com.yihe.system.service.NewServiceFormForOsteoporosisPatientsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewServiceFormForOsteoporosisPatientsServiceImpl implements NewServiceFormForOsteoporosisPatientsService {
    @Resource
    NewServiceFormForOsteoporosisPatientsMapper newServiceFormForOsteoporosisPatientsMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;
    @Override
    public int insetNew(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newServiceFormForOsteoporosisPatients.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newServiceFormForOsteoporosisPatientsMapper.insertSelective(newServiceFormForOsteoporosisPatients);
    }

    @Override
    public int updateNew(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients) {
        return newServiceFormForOsteoporosisPatientsMapper.updateByPrimaryKeySelective(newServiceFormForOsteoporosisPatients);
    }

    @Override
    public NewServiceFormForOsteoporosisPatients selectById(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients) {
        return newServiceFormForOsteoporosisPatientsMapper.selectByPrimaryKey(newServiceFormForOsteoporosisPatients.getId());
    }

    @Override
    public List<NewServiceFormForOsteoporosisPatients> selectByPage(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients) {
        return newServiceFormForOsteoporosisPatientsMapper.selectByPage(newServiceFormForOsteoporosisPatients);
    }

    @Override
    public List<NewServiceFormForOsteoporosisPatients> selectAll() {
        return newServiceFormForOsteoporosisPatientsMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewServiceFormForOsteoporosisPatients newServiceFormForOsteoporosisPatients) {
        return newServiceFormForOsteoporosisPatientsMapper.selectByPageCount(newServiceFormForOsteoporosisPatients);
    }
}
