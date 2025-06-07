package com.yihe.system.mapper;


import com.yihe.system.domain.NewType2DiabetesPatientServiceRecord;

import java.util.List;

public interface NewType2DiabetesPatientServiceRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewType2DiabetesPatientServiceRecord row);

    int insertSelective(NewType2DiabetesPatientServiceRecord row);

    NewType2DiabetesPatientServiceRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewType2DiabetesPatientServiceRecord row);

    int updateByPrimaryKey(NewType2DiabetesPatientServiceRecord row);

    List<NewType2DiabetesPatientServiceRecord> selectByPage(NewType2DiabetesPatientServiceRecord row);

    List<NewType2DiabetesPatientServiceRecord> selectAll();

    int selectByPageCount(NewType2DiabetesPatientServiceRecord row);
}