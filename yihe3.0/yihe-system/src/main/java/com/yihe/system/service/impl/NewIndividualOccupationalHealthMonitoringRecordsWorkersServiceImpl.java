package com.yihe.system.service.impl;

import com.yihe.system.domain.NewIndividualOccupationalHealthMonitoringRecordsWorkers;
import com.yihe.system.mapper.NewIndividualOccupationalHealthMonitoringRecordsWorkersMapper;
import com.yihe.system.service.NewIndividualOccupationalHealthMonitoringRecordsWorkersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewIndividualOccupationalHealthMonitoringRecordsWorkersServiceImpl implements NewIndividualOccupationalHealthMonitoringRecordsWorkersService {
    @Resource
    NewIndividualOccupationalHealthMonitoringRecordsWorkersMapper newIndividualOccupationalHealthMonitoringRecordsWorkersMapper;


    @Override
    public int insetNew(NewIndividualOccupationalHealthMonitoringRecordsWorkers newIndividualOccupationalHealthMonitoringRecordsWorkers) {
        return newIndividualOccupationalHealthMonitoringRecordsWorkersMapper.insertSelective(newIndividualOccupationalHealthMonitoringRecordsWorkers);
    }

    @Override
    public int updateNew(NewIndividualOccupationalHealthMonitoringRecordsWorkers newIndividualOccupationalHealthMonitoringRecordsWorkers) {
        return newIndividualOccupationalHealthMonitoringRecordsWorkersMapper.updateByPrimaryKeySelective(newIndividualOccupationalHealthMonitoringRecordsWorkers);
    }

    @Override
    public NewIndividualOccupationalHealthMonitoringRecordsWorkers selectById(NewIndividualOccupationalHealthMonitoringRecordsWorkers newIndividualOccupationalHealthMonitoringRecordsWorkers) {
        return newIndividualOccupationalHealthMonitoringRecordsWorkersMapper.selectByPrimaryKey(newIndividualOccupationalHealthMonitoringRecordsWorkers.getId());
    }

    @Override
    public List<NewIndividualOccupationalHealthMonitoringRecordsWorkers> selectByPage(NewIndividualOccupationalHealthMonitoringRecordsWorkers newIndividualOccupationalHealthMonitoringRecordsWorkers) {
        return newIndividualOccupationalHealthMonitoringRecordsWorkersMapper.selectByPage(newIndividualOccupationalHealthMonitoringRecordsWorkers);
    }

    @Override
    public int selectByPageCount(NewIndividualOccupationalHealthMonitoringRecordsWorkers newIndividualOccupationalHealthMonitoringRecordsWorkers) {
        return newIndividualOccupationalHealthMonitoringRecordsWorkersMapper.selectByPageCount(newIndividualOccupationalHealthMonitoringRecordsWorkers);
    }
}
