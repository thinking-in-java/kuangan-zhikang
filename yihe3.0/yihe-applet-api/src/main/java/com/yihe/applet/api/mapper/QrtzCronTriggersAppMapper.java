package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.QrtzCronTriggersApp;
import com.yihe.applet.api.domain.QrtzCronTriggersAppKey;

public interface QrtzCronTriggersAppMapper {
    int deleteByPrimaryKey(QrtzCronTriggersAppKey key);

    int insert(QrtzCronTriggersApp record);

    int insertSelective(QrtzCronTriggersApp record);

    QrtzCronTriggersApp selectByPrimaryKey(QrtzCronTriggersAppKey key);

    int updateByPrimaryKeySelective(QrtzCronTriggersApp record);

    int updateByPrimaryKey(QrtzCronTriggersApp record);
}