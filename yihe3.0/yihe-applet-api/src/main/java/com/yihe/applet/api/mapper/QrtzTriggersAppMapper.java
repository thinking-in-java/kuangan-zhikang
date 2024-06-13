package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.QrtzTriggersApp;
import com.yihe.applet.api.domain.QrtzTriggersAppKey;

public interface QrtzTriggersAppMapper {
    int deleteByPrimaryKey(QrtzTriggersAppKey key);

    int insert(QrtzTriggersApp record);

    int insertSelective(QrtzTriggersApp record);

    QrtzTriggersApp selectByPrimaryKey(QrtzTriggersAppKey key);

    int updateByPrimaryKeySelective(QrtzTriggersApp record);

    int updateByPrimaryKeyWithBLOBs(QrtzTriggersApp record);

    int updateByPrimaryKey(QrtzTriggersApp record);
}