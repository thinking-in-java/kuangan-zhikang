package com.yihe.system.mapper;

import com.yihe.system.domain.NewDermatosisPatientServiceRecordForm;
import com.yihe.system.domain.NewListChronicDiseasePatients;

import java.util.List;

public interface NewDermatosisPatientServiceRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewDermatosisPatientServiceRecordForm row);

    int insertSelective(NewDermatosisPatientServiceRecordForm row);

    NewDermatosisPatientServiceRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewDermatosisPatientServiceRecordForm row);

    int updateByPrimaryKey(NewDermatosisPatientServiceRecordForm row);

    List<NewDermatosisPatientServiceRecordForm> selectByPage(NewDermatosisPatientServiceRecordForm newListChronicDiseasePatients);

    List<NewDermatosisPatientServiceRecordForm> selectAll();

    int selectByPageCount(NewDermatosisPatientServiceRecordForm newListChronicDiseasePatients);
}