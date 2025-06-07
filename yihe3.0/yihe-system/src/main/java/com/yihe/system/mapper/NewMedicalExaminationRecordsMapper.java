package com.yihe.system.mapper;


import com.yihe.system.domain.NewMedicalExaminationRecords;

import java.util.List;

public interface NewMedicalExaminationRecordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewMedicalExaminationRecords row);

    int insertSelective(NewMedicalExaminationRecords row);

    NewMedicalExaminationRecords selectByPrimaryKey(Integer id);

    NewMedicalExaminationRecords selectByPrimaryKeyId(Integer id);

    int updateByPrimaryKeySelective(NewMedicalExaminationRecords row);

    int updateByPrimaryKey(NewMedicalExaminationRecords row);

    List<NewMedicalExaminationRecords> selectByPage(NewMedicalExaminationRecords row);

    int selectByPageCount(NewMedicalExaminationRecords newMedicalExaminationRecords);

    List<NewMedicalExaminationRecords> selectArchives();
}