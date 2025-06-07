package com.yihe.system.mapper;


import com.yihe.system.domain.NewPneumoconiosisServiceRecordForm;

import java.util.List;

public interface NewPneumoconiosisServiceRecordFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewPneumoconiosisServiceRecordForm row);

    int insertSelective(NewPneumoconiosisServiceRecordForm row);

    NewPneumoconiosisServiceRecordForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewPneumoconiosisServiceRecordForm row);

    int updateByPrimaryKey(NewPneumoconiosisServiceRecordForm row);

    List<NewPneumoconiosisServiceRecordForm> selectByPage(NewPneumoconiosisServiceRecordForm row);

    List<NewPneumoconiosisServiceRecordForm> selectAll();

    int selectByPageCount(NewPneumoconiosisServiceRecordForm row);
}