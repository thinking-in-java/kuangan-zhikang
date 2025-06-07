package com.yihe.system.mapper;


import com.yihe.system.domain.NewCoronaryHeartDiseasePatients;

import java.util.List;

public interface NewCoronaryHeartDiseasePatientsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewCoronaryHeartDiseasePatients row);

    int insertSelective(NewCoronaryHeartDiseasePatients row);

    NewCoronaryHeartDiseasePatients selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewCoronaryHeartDiseasePatients row);

    int updateByPrimaryKey(NewCoronaryHeartDiseasePatients row);

    List<NewCoronaryHeartDiseasePatients> selectByPage(NewCoronaryHeartDiseasePatients row);

    List<NewCoronaryHeartDiseasePatients> selectAll();

    int selectByPageCount(NewCoronaryHeartDiseasePatients row);
}