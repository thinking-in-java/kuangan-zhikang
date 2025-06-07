package com.yihe.system.service;

import com.yihe.system.domain.NewInterventionRecordFormForHighRiskPopulation;
import com.yihe.system.domain.NewRisk;

import java.util.List;

public interface NewInterventionRecordFormForHighRiskPopulationService {
    int insetNew(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation);

    int updateNew(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation);

    NewInterventionRecordFormForHighRiskPopulation selectById(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation);

    List<NewInterventionRecordFormForHighRiskPopulation> selectByPage(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation);

    List<NewInterventionRecordFormForHighRiskPopulation> selectAll();

    int selectByPageCount(NewInterventionRecordFormForHighRiskPopulation newInterventionRecordFormForHighRiskPopulation);
}
