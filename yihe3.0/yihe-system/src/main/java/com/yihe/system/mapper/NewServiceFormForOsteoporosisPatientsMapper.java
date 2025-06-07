package com.yihe.system.mapper;


import com.yihe.system.domain.NewServiceFormForOsteoporosisPatients;
import com.yihe.system.domain.NewServiceRecordFormForOsteoarthritisPatients;

import java.util.List;

public interface NewServiceFormForOsteoporosisPatientsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewServiceFormForOsteoporosisPatients row);

    int insertSelective(NewServiceFormForOsteoporosisPatients row);

    NewServiceFormForOsteoporosisPatients selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewServiceFormForOsteoporosisPatients row);

    int updateByPrimaryKey(NewServiceFormForOsteoporosisPatients row);

    List<NewServiceFormForOsteoporosisPatients> selectByPage(NewServiceFormForOsteoporosisPatients row);

    List<NewServiceFormForOsteoporosisPatients> selectAll();

    int selectByPageCount(NewServiceFormForOsteoporosisPatients row);
}