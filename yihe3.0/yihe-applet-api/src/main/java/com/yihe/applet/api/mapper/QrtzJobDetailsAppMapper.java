package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.QrtzJobDetailsApp;
import com.yihe.applet.api.domain.QrtzJobDetailsAppKey;

public interface QrtzJobDetailsAppMapper {
    int deleteByPrimaryKey(QrtzJobDetailsAppKey key);

    int insert(QrtzJobDetailsApp record);

    int insertSelective(QrtzJobDetailsApp record);

    QrtzJobDetailsApp selectByPrimaryKey(QrtzJobDetailsAppKey key);

    int updateByPrimaryKeySelective(QrtzJobDetailsApp record);

    int updateByPrimaryKeyWithBLOBs(QrtzJobDetailsApp record);

    int updateByPrimaryKey(QrtzJobDetailsApp record);
}