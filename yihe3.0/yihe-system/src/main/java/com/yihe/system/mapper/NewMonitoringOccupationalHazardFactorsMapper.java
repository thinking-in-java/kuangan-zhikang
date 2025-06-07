package com.yihe.system.mapper;


import com.yihe.system.domain.NewIndividualOccupationalHealthMonitoringRecordsWorkers;
import com.yihe.system.domain.NewMonitoringOccupationalHazardFactors;
import com.yihe.system.domain.NewOccupationalHealthManagementArchives;

import java.util.List;

public interface NewMonitoringOccupationalHazardFactorsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewMonitoringOccupationalHazardFactors row);

    int insertSelective(NewMonitoringOccupationalHazardFactors row);

    NewMonitoringOccupationalHazardFactors selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewMonitoringOccupationalHazardFactors row);

    int updateByPrimaryKeyWithBLOBs(NewMonitoringOccupationalHazardFactors row);

    int updateByPrimaryKey(NewMonitoringOccupationalHazardFactors row);

    List<NewMonitoringOccupationalHazardFactors> selectByPage(NewMonitoringOccupationalHazardFactors row);

    int selectByPageCount(NewMonitoringOccupationalHazardFactors row);

    NewMonitoringOccupationalHazardFactors selectOrder();
}