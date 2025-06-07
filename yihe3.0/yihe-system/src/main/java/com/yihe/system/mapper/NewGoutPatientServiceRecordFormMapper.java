package com.yihe.system.mapper;


import com.yihe.system.domain.NewGoutPatientServiceRecordForm;

import java.util.List;

public interface NewGoutPatientServiceRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewGoutPatientServiceRecordForm row);

    int insertSelective(NewGoutPatientServiceRecordForm row);

    NewGoutPatientServiceRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewGoutPatientServiceRecordForm row);

    int updateByPrimaryKey(NewGoutPatientServiceRecordForm row);

    List<NewGoutPatientServiceRecordForm> selectByPage(NewGoutPatientServiceRecordForm row);

    List<NewGoutPatientServiceRecordForm> selectAll();

    int selectByPageCount(NewGoutPatientServiceRecordForm row);
}