package com.yihe.system.mapper;


import com.yihe.system.domain.NewInsomniaInterventionRecordForm;

import java.util.List;

public interface NewInsomniaInterventionRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewInsomniaInterventionRecordForm row);

    int insertSelective(NewInsomniaInterventionRecordForm row);

    NewInsomniaInterventionRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewInsomniaInterventionRecordForm row);

    int updateByPrimaryKey(NewInsomniaInterventionRecordForm row);

    List<NewInsomniaInterventionRecordForm> selectByPage(NewInsomniaInterventionRecordForm row);

    List<NewInsomniaInterventionRecordForm> selectAll();

    int selectByPageCount(NewInsomniaInterventionRecordForm row);
}