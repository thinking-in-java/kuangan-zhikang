package com.yihe.system.service.impl;

import com.yihe.system.domain.TOnemachineData;
import com.yihe.system.mapper.TOnemachineDataMapper;
import com.yihe.system.service.TOnemachineDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TOnemachineDataServiceImpl implements TOnemachineDataService {
    @Autowired
    private TOnemachineDataMapper tOnemachineDataMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return tOnemachineDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TOnemachineData record) {
        return tOnemachineDataMapper.insert(record);
    }

    @Override
    public int insertSelective(TOnemachineData record) {
        return tOnemachineDataMapper.insertSelective(record);
    }

    @Override
    public TOnemachineData selectByPrimaryKey(Long id) {
        return tOnemachineDataMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TOnemachineData record) {
        return tOnemachineDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TOnemachineData record) {
        return tOnemachineDataMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TOnemachineData> queryOnemachine(String userCode) {
        return tOnemachineDataMapper.queryOnemachine(userCode);
    }
}
