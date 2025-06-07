package com.yihe.system.mapper;


import com.yihe.system.domain.NewListPsychologicalPatients;
import com.yihe.system.domain.NewObesityPatientServiceRecord;

import java.util.List;

public interface NewListPsychologicalPatientsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewListPsychologicalPatients row);

    int insertSelective(NewListPsychologicalPatients row);

    NewListPsychologicalPatients selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewListPsychologicalPatients row);

    int updateByPrimaryKey(NewListPsychologicalPatients row);

    List<NewListPsychologicalPatients> selectByPage(NewListPsychologicalPatients row);

    int selectByPageCount(NewListPsychologicalPatients row);
}