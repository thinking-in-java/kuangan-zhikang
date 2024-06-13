package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserHemoglobin;
import com.yihe.system.mapper.YhUserHemoglobinMapper;
import com.yihe.system.service.yHUserHemoglobinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class yHUserHemoglobinServiceImpl implements yHUserHemoglobinService {
    @Autowired
    YhUserHemoglobinMapper yhUserHemoglobinMapper;

    @Override
    public Map<String,Object> selectUserHemoglobin(String userCode) {
        return yhUserHemoglobinMapper.selectUserHemoglobin(userCode);
    }

    @Override
    public PageInfo<YhUserHemoglobin> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
        //设置分页参数
        PageHelper.startPage(page,limit);
        List<YhUserHemoglobin> hemoglobinList = yhUserHemoglobinMapper.selectPageByParam(userCode);
        return new PageInfo<YhUserHemoglobin>(hemoglobinList);
    }

    @Override
    public PageInfo<YhUserHemoglobin> selectHemoglobin(String userCode, int page, int limit) {
        // TODO Auto-generated method stub
        //设置分页参数
        PageHelper.startPage(page,limit);
        List<YhUserHemoglobin> list = yhUserHemoglobinMapper.selectHemoglobin(userCode);
        return new PageInfo<YhUserHemoglobin>(list);
    }

    @Override
    public YhUserHemoglobin selectByPrimaryKeyByUsercodeLimit(String userCode) {
        return yhUserHemoglobinMapper.selectByPrimaryKeyByUsercodeLimit(userCode);
    }

    @Override
    public int insertSelective(YhUserHemoglobin record) {
        return yhUserHemoglobinMapper.insertSelective(record);
    }
}
