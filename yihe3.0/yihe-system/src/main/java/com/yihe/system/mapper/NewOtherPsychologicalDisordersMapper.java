package com.yihe.system.mapper;


import com.yihe.system.domain.NewOtherPsychologicalDisorders;
import com.yihe.system.domain.NewStressInterventionRecordForm;

import java.util.List;

public interface NewOtherPsychologicalDisordersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewOtherPsychologicalDisorders row);

    int insertSelective(NewOtherPsychologicalDisorders row);

    NewOtherPsychologicalDisorders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewOtherPsychologicalDisorders row);

    int updateByPrimaryKey(NewOtherPsychologicalDisorders row);

    List<NewOtherPsychologicalDisorders> selectByPage(NewOtherPsychologicalDisorders row);

    int selectByPageCount(NewOtherPsychologicalDisorders row);
}