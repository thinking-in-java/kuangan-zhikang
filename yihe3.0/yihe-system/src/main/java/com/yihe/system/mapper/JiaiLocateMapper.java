package com.yihe.system.mapper;

import com.yihe.system.domain.JiaiLocate;

public interface JiaiLocateMapper {
    int insert(JiaiLocate record);

    int insertSelective(JiaiLocate record);

    JiaiLocate findNewLocate(String imei);
}