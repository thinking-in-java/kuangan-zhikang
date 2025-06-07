package com.yihe.system.mapper;


import com.yihe.system.domain.NewProstatitisPatientServiceRecordForm;
import com.yihe.system.domain.NewRecordFormForChronicGastritisPatients;

import java.util.List;

public interface NewProstatitisPatientServiceRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewProstatitisPatientServiceRecordForm row);

    int insertSelective(NewProstatitisPatientServiceRecordForm row);

    NewProstatitisPatientServiceRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewProstatitisPatientServiceRecordForm row);

    int updateByPrimaryKey(NewProstatitisPatientServiceRecordForm row);

    List<NewProstatitisPatientServiceRecordForm> selectByPage(NewProstatitisPatientServiceRecordForm newListChronicDiseasePatients);

    List<NewProstatitisPatientServiceRecordForm> selectAll();

    int selectByPageCount(NewProstatitisPatientServiceRecordForm newListChronicDiseasePatients);
}