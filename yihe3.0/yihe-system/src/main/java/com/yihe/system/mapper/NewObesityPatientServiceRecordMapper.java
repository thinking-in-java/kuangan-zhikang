package com.yihe.system.mapper;


import com.yihe.system.domain.NewObesityPatientServiceRecord;

import java.util.List;

public interface NewObesityPatientServiceRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewObesityPatientServiceRecord row);

    int insertSelective(NewObesityPatientServiceRecord row);

    NewObesityPatientServiceRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewObesityPatientServiceRecord row);

    int updateByPrimaryKey(NewObesityPatientServiceRecord row);

    List<NewObesityPatientServiceRecord> selectByPage(NewObesityPatientServiceRecord row);

    List<NewObesityPatientServiceRecord> selectAll();

    int selectByPageCount(NewObesityPatientServiceRecord row);
}