package com.yihe.system.mapper;


import com.yihe.system.domain.NewPatientsWithAbnormalBloodLipids;

import java.util.List;

public interface NewPatientsWithAbnormalBloodLipidsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewPatientsWithAbnormalBloodLipids row);

    int insertSelective(NewPatientsWithAbnormalBloodLipids row);

    NewPatientsWithAbnormalBloodLipids selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewPatientsWithAbnormalBloodLipids row);

    int updateByPrimaryKey(NewPatientsWithAbnormalBloodLipids row);

    List<NewPatientsWithAbnormalBloodLipids> selectByPage(NewPatientsWithAbnormalBloodLipids row);

    List<NewPatientsWithAbnormalBloodLipids> selectAll();

    int selectByPageCount(NewPatientsWithAbnormalBloodLipids row);
}