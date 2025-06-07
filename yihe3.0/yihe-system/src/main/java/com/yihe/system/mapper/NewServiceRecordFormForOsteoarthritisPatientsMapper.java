package com.yihe.system.mapper;


import com.yihe.system.domain.NewServiceRecordFormForHypertensivePatients;
import com.yihe.system.domain.NewServiceRecordFormForOsteoarthritisPatients;

import java.util.List;

public interface NewServiceRecordFormForOsteoarthritisPatientsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewServiceRecordFormForOsteoarthritisPatients row);

    int insertSelective(NewServiceRecordFormForOsteoarthritisPatients row);

    NewServiceRecordFormForOsteoarthritisPatients selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewServiceRecordFormForOsteoarthritisPatients row);

    int updateByPrimaryKey(NewServiceRecordFormForOsteoarthritisPatients row);

    List<NewServiceRecordFormForOsteoarthritisPatients> selectByPage(NewServiceRecordFormForOsteoarthritisPatients row);

    List<NewServiceRecordFormForOsteoarthritisPatients> selectAll();

    int selectByPageCount(NewServiceRecordFormForOsteoarthritisPatients row);
}