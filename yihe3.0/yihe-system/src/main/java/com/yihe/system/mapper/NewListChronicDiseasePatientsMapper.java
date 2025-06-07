package com.yihe.system.mapper;


import com.yihe.system.domain.NewListChronicDiseasePatients;

import java.util.List;

public interface NewListChronicDiseasePatientsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewListChronicDiseasePatients row);

    int insertSelective(NewListChronicDiseasePatients row);

    NewListChronicDiseasePatients selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewListChronicDiseasePatients row);

    int updateByPrimaryKey(NewListChronicDiseasePatients row);

    List<NewListChronicDiseasePatients> selectByPage(NewListChronicDiseasePatients newListChronicDiseasePatients);

    int selectByPageCount(NewListChronicDiseasePatients newListChronicDiseasePatients);
}