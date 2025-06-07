package com.yihe.system.mapper;


import com.yihe.system.domain.NewRecordFormForChronicGastritisPatients;

import java.util.List;

public interface NewRecordFormForChronicGastritisPatientsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewRecordFormForChronicGastritisPatients row);

    int insertSelective(NewRecordFormForChronicGastritisPatients row);

    NewRecordFormForChronicGastritisPatients selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewRecordFormForChronicGastritisPatients row);

    int updateByPrimaryKey(NewRecordFormForChronicGastritisPatients row);

    List<NewRecordFormForChronicGastritisPatients> selectByPage(NewRecordFormForChronicGastritisPatients newListChronicDiseasePatients);

    List<NewRecordFormForChronicGastritisPatients> selectAll();

    int selectByPageCount(NewRecordFormForChronicGastritisPatients newListChronicDiseasePatients);
}