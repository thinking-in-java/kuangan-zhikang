package com.yihe.system.service.impl;

import com.yihe.system.domain.NewInterventionRecordFormForHighRiskPopulation;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewInterventionRecordFormForHighRiskPopulationMapper;
import com.yihe.system.mapper.newRegMapper;
import com.yihe.system.service.NewInterventionRecordFormForHighRiskPopulationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewInterventionRecordFormForHighRiskPopulationServiceImpl implements NewInterventionRecordFormForHighRiskPopulationService {
    @Resource
    NewInterventionRecordFormForHighRiskPopulationMapper newInterventionRecordFormForHighRiskPopulationMapper;
    @Resource
    newRegMapper newRegMapper;

    @Override
    public int insetNew(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newInterventionRecordFormForHighRiskPopulation.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newInterventionRecordFormForHighRiskPopulationMapper.insertSelective(newInterventionRecordFormForHighRiskPopulation);
    }

    @Override
    public int updateNew(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation) {
        return newInterventionRecordFormForHighRiskPopulationMapper.updateByPrimaryKeySelective(newInterventionRecordFormForHighRiskPopulation);
    }

    @Override
    public NewInterventionRecordFormForHighRiskPopulation selectById(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation) {
        return newInterventionRecordFormForHighRiskPopulationMapper.selectByPrimaryKey(newInterventionRecordFormForHighRiskPopulation.getId());
    }

    @Override
    public List<NewInterventionRecordFormForHighRiskPopulation> selectByPage(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation) {
        return newInterventionRecordFormForHighRiskPopulationMapper.selectByPage(newInterventionRecordFormForHighRiskPopulation);
    }

    @Override
    public List<NewInterventionRecordFormForHighRiskPopulation> selectAll() {
        return newInterventionRecordFormForHighRiskPopulationMapper.selectAll();
    }

    @Override
    public int selectByPageCount(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation) {
        return newInterventionRecordFormForHighRiskPopulationMapper.selectByPageCount(newInterventionRecordFormForHighRiskPopulation);
    }
}
