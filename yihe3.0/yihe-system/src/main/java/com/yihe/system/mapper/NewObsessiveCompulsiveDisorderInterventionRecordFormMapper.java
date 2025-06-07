package com.yihe.system.mapper;


import com.yihe.system.domain.NewAnxietyDisorderInterventionRecordForm;
import com.yihe.system.domain.NewObsessiveCompulsiveDisorderInterventionRecordForm;

import java.util.List;

public interface NewObsessiveCompulsiveDisorderInterventionRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewObsessiveCompulsiveDisorderInterventionRecordForm row);

    int insertSelective(NewObsessiveCompulsiveDisorderInterventionRecordForm row);

    NewObsessiveCompulsiveDisorderInterventionRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewObsessiveCompulsiveDisorderInterventionRecordForm row);

    int updateByPrimaryKey(NewObsessiveCompulsiveDisorderInterventionRecordForm row);

    List<NewObsessiveCompulsiveDisorderInterventionRecordForm> selectByPage(NewObsessiveCompulsiveDisorderInterventionRecordForm row);

    int selectByPageCount(NewObsessiveCompulsiveDisorderInterventionRecordForm row);
}