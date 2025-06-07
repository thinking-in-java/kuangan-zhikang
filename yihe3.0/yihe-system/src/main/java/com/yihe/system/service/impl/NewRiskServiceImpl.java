package com.yihe.system.service.impl;

import com.yihe.system.domain.NewRisk;
import com.yihe.system.domain.NewThreeSimultaneous;
import com.yihe.system.mapper.NewRiskMapper;
import com.yihe.system.service.NewRiskSerVice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewRiskServiceImpl implements NewRiskSerVice {
    @Resource
    NewRiskMapper newRiskMapper;

    @Override
    public int insetNew(NewRisk newRisk) {
        return newRiskMapper.insertSelective(newRisk);
    }

    @Override
    public int updateNew(NewRisk newRisk) {
        return newRiskMapper.updateByPrimaryKeySelective(newRisk);
    }

    @Override
    public NewRisk selectById(NewRisk newRisk) {
        return newRiskMapper.selectByPrimaryKey(newRisk.getId());
    }

    @Override
    public List<NewRisk> selectByPage(NewRisk newRisk) {
        return newRiskMapper.selectByPage(newRisk);
    }

    @Override
    public int selectByPageCount(NewRisk newRisk) {
        return newRiskMapper.selectByPageCount(newRisk);
    }
}
