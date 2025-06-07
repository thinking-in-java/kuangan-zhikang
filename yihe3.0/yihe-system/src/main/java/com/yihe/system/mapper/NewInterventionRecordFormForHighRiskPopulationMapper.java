package com.yihe.system.mapper;


import com.yihe.system.domain.NewInterventionRecordFormForHighRiskPopulation;

import java.util.List;

public interface NewInterventionRecordFormForHighRiskPopulationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewInterventionRecordFormForHighRiskPopulation row);

    int insertSelective(NewInterventionRecordFormForHighRiskPopulation row);

    NewInterventionRecordFormForHighRiskPopulation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewInterventionRecordFormForHighRiskPopulation row);

    int updateByPrimaryKey(NewInterventionRecordFormForHighRiskPopulation row);

    List<NewInterventionRecordFormForHighRiskPopulation> selectByPage(NewInterventionRecordFormForHighRiskPopulation row);

    List<NewInterventionRecordFormForHighRiskPopulation> selectAll();

    int selectByPageCount(NewInterventionRecordFormForHighRiskPopulation row);

    int selectCountALl();

    int selectAnxI();

    int selectOb();

    int selectNd();

    int selectSI();

    int selectII();

    int selectOP();

    int selectEP();

    Integer selectCountALlBySex(Integer sex);
}