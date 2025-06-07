package com.yihe.system.service;

import com.yihe.system.domain.NewObsessiveCompulsiveDisorderInterventionRecordForm;
import com.yihe.system.domain.NewOtherPsychologicalDisorders;

import java.util.List;

public interface NewOtherPsychologicalDisordersService {
    int insetNew(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders);

    int updateNew(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders);

    NewOtherPsychologicalDisorders selectById(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders);

    List<NewOtherPsychologicalDisorders> selectByPage(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders);

    int selectByPageCount(NewOtherPsychologicalDisorders newOtherPsychologicalDisorders);
}
