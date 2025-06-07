package com.yihe.system.mapper;


import com.yihe.system.domain.NewRecordFormForChronicGastritisPatients;
import com.yihe.system.domain.NewServiceRecordFormForBreastHyperplasiaPatients;

import java.util.List;

public interface NewServiceRecordFormForBreastHyperplasiaPatientsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewServiceRecordFormForBreastHyperplasiaPatients row);

    int insertSelective(NewServiceRecordFormForBreastHyperplasiaPatients row);

    NewServiceRecordFormForBreastHyperplasiaPatients selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewServiceRecordFormForBreastHyperplasiaPatients row);

    int updateByPrimaryKey(NewServiceRecordFormForBreastHyperplasiaPatients row);

    List<NewServiceRecordFormForBreastHyperplasiaPatients> selectByPage(NewServiceRecordFormForBreastHyperplasiaPatients newListChronicDiseasePatients);

    List<NewServiceRecordFormForBreastHyperplasiaPatients> selectAll();

    int selectByPageCount(NewServiceRecordFormForBreastHyperplasiaPatients newListChronicDiseasePatients);
}