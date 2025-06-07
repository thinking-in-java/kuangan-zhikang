package com.yihe.system.service;

import com.yihe.system.domain.NewIndividualOccupationalHealthMonitoringRecordsWorkers;
import com.yihe.system.domain.NewMonitoringOccupationalHazardFactors;

import java.util.List;

public interface NewMonitoringOccupationalHazardFactorsService {

    int insetNew(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors);

    int updateNew(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors);

    NewMonitoringOccupationalHazardFactors selectById(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors);

    List<NewMonitoringOccupationalHazardFactors> selectByPage(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors);

    int selectByPageCount(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors);

    int deleteById(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors);
}
