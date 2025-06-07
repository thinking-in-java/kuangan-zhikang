package com.yihe.system.service.impl;

import com.yihe.system.domain.NewEmployeePersonalInformationCard;
import com.yihe.system.domain.NewInterventionRecordFormForHighRiskPopulation;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewEmployeePersonalInformationCardMapper;
import com.yihe.system.mapper.NewInterventionRecordFormForHighRiskPopulationMapper;
import com.yihe.system.mapper.newRegMapper;
import com.yihe.system.service.NewEmployeePersonalInformationCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewEmployeePersonalInformationCardServiceImpl implements NewEmployeePersonalInformationCardService {
    @Resource
    NewEmployeePersonalInformationCardMapper newEmployeePersonalInformationCardMapper;


    @Override
    public int insetNew(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        return newEmployeePersonalInformationCardMapper.insertSelective(newEmployeePersonalInformationCard);
    }

    @Override
    public int updateNew(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        return newEmployeePersonalInformationCardMapper.updateByPrimaryKeySelective(newEmployeePersonalInformationCard);
    }

    @Override
    public int deleteById(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard) {
        return newEmployeePersonalInformationCardMapper.deleteByPrimaryKey(newEmployeePersonalInformationCard.getId());
    }

    @Override
    public NewEmployeePersonalInformationCard selectById(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        return newEmployeePersonalInformationCardMapper.selectByPrimaryKey(newEmployeePersonalInformationCard.getId());
    }

    @Override
    public Integer selectId(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard) {
        return newEmployeePersonalInformationCardMapper.selectId(newEmployeePersonalInformationCard);
    }

    @Override
    public Integer inserCount(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard) {
        return newEmployeePersonalInformationCardMapper.inserCount(newEmployeePersonalInformationCard);
    }

    @Override
    public List<NewEmployeePersonalInformationCard> selectByPage(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        return newEmployeePersonalInformationCardMapper.selectByPage(newEmployeePersonalInformationCard);
    }

    @Override
    public int selectByPageCount(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        return newEmployeePersonalInformationCardMapper.selectByPageCount(newEmployeePersonalInformationCard);
    }
}
