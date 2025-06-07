package com.yihe.system.service;

import com.yihe.system.domain.NewPneumoconiosisServiceRecordForm;

import java.util.List;

public interface NewPneumoconiosisServiceRecordFormService {
    int insetNew(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm);

    int updateNew(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm);

    NewPneumoconiosisServiceRecordForm selectById(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm);

    List<NewPneumoconiosisServiceRecordForm> selectByPage(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm);

    int selectByPageCount(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm);

    List<NewPneumoconiosisServiceRecordForm> selectAll();
}
