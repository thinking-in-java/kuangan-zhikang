package com.yihe.system.service;

import com.yihe.system.domain.newDepressionInterventionRecordForm;

import java.util.List;

public interface newDepressionInterventionRecordFormService {
    int insetNew(newDepressionInterventionRecordForm newDepressionInterventionRecordForm);

    int updateNew(newDepressionInterventionRecordForm newDepressionInterventionRecordForm);

    newDepressionInterventionRecordForm selectById(newDepressionInterventionRecordForm newDepressionInterventionRecordForm);

    List<newDepressionInterventionRecordForm> selectByPage(newDepressionInterventionRecordForm newDepressionInterventionRecordForm);

    int selectByPageCount(newDepressionInterventionRecordForm newDepressionInterventionRecordForm);
}
