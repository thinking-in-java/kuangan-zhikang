package com.yihe.system.mapper;


import com.yihe.system.domain.NewEmployeePsychologicalCounselingRecordForm;
import com.yihe.system.domain.NewGoutPatientServiceRecordForm;

import java.util.List;

public interface NewEmployeePsychologicalCounselingRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewEmployeePsychologicalCounselingRecordForm row);

    int insertSelective(NewEmployeePsychologicalCounselingRecordForm row);

    NewEmployeePsychologicalCounselingRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewEmployeePsychologicalCounselingRecordForm row);

    int updateByPrimaryKey(NewEmployeePsychologicalCounselingRecordForm row);

    List<NewEmployeePsychologicalCounselingRecordForm> selectByPage(NewEmployeePsychologicalCounselingRecordForm row);

    int selectByPageCount(NewEmployeePsychologicalCounselingRecordForm row);
}