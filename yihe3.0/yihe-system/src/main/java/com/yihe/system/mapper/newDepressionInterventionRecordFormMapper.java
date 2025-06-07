package com.yihe.system.mapper;


import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.newDepressionInterventionRecordForm;

import java.util.List;

public interface newDepressionInterventionRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(newDepressionInterventionRecordForm row);

    int insertSelective(newDepressionInterventionRecordForm row);

    newDepressionInterventionRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(newDepressionInterventionRecordForm row);

    int updateByPrimaryKey(newDepressionInterventionRecordForm row);

    List<newDepressionInterventionRecordForm> selectByPage(newDepressionInterventionRecordForm row);

    int selectByPageCount(newDepressionInterventionRecordForm row);
}