package com.yihe.system.mapper;


import com.yihe.system.domain.NewIndividualOccupationalHealthMonitoringRecordsWorkers;

import java.util.List;

public interface NewIndividualOccupationalHealthMonitoringRecordsWorkersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewIndividualOccupationalHealthMonitoringRecordsWorkers row);

    int insertSelective(NewIndividualOccupationalHealthMonitoringRecordsWorkers row);

    NewIndividualOccupationalHealthMonitoringRecordsWorkers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewIndividualOccupationalHealthMonitoringRecordsWorkers row);

    int updateByPrimaryKeyWithBLOBs(NewIndividualOccupationalHealthMonitoringRecordsWorkers row);

    int updateByPrimaryKey(NewIndividualOccupationalHealthMonitoringRecordsWorkers row);

    List<NewIndividualOccupationalHealthMonitoringRecordsWorkers> selectByPage(NewIndividualOccupationalHealthMonitoringRecordsWorkers row);

    int selectByPageCount(NewIndividualOccupationalHealthMonitoringRecordsWorkers row);
}