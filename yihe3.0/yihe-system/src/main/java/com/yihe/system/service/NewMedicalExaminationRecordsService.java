package com.yihe.system.service;

import com.yihe.system.domain.NewMedicalExaminationRecords;

import java.util.List;

public interface NewMedicalExaminationRecordsService {

    List<NewMedicalExaminationRecords> selectByPage(NewMedicalExaminationRecords newMedicalExaminationRecords,Integer page,Integer limit);

    int selectByPageCount(NewMedicalExaminationRecords newMedicalExaminationRecords);

    NewMedicalExaminationRecords selectById(NewMedicalExaminationRecords newMedicalExaminationRecords);

    int insert(NewMedicalExaminationRecords newMedicalExaminationRecords);

    int updateById(NewMedicalExaminationRecords newMedicalExaminationRecords);

    int deleteById(NewMedicalExaminationRecords newMedicalExaminationRecords);
}
