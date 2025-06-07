package com.yihe.system.mapper;


import com.yihe.system.domain.NewObsessiveCompulsiveDisorderInterventionRecordForm;
import com.yihe.system.domain.NewStressInterventionRecordForm;

import java.util.List;

public interface NewStressInterventionRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewStressInterventionRecordForm row);

    int insertSelective(NewStressInterventionRecordForm row);

    NewStressInterventionRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewStressInterventionRecordForm row);

    int updateByPrimaryKey(NewStressInterventionRecordForm row);

    List<NewStressInterventionRecordForm> selectByPage(NewStressInterventionRecordForm row);

    int selectByPageCount(NewStressInterventionRecordForm row);
}