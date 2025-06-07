package com.yihe.system.service.impl;

import com.yihe.system.domain.NewOtherPsychologicalDisorders;
import com.yihe.system.mapper.NewOtherPsychologicalDisordersMapper;
import com.yihe.system.service.NewOtherPsychologicalDisordersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewOtherPsychologicalDisordersServiceImpl implements NewOtherPsychologicalDisordersService {

    @Resource
    NewOtherPsychologicalDisordersMapper newOtherPsychologicalDisordersMapper;

    @Override
    public int insetNew(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders) {
        return newOtherPsychologicalDisordersMapper.insertSelective(newOtherPsychologicalDisorders);
    }

    @Override
    public int updateNew(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders) {
        return newOtherPsychologicalDisordersMapper.updateByPrimaryKeySelective(newOtherPsychologicalDisorders);
    }

    @Override
    public NewOtherPsychologicalDisorders selectById(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders) {
        return newOtherPsychologicalDisordersMapper.selectByPrimaryKey(newOtherPsychologicalDisorders.getId());
    }

    @Override
    public List<NewOtherPsychologicalDisorders> selectByPage(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders) {
        return newOtherPsychologicalDisordersMapper.selectByPage(newOtherPsychologicalDisorders);
    }

    @Override
    public int selectByPageCount(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders) {
        return newOtherPsychologicalDisordersMapper.selectByPageCount(newOtherPsychologicalDisorders);
    }
}
