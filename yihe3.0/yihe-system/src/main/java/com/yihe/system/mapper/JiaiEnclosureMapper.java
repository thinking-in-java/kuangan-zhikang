package com.yihe.system.mapper;

import com.yihe.system.domain.JiaiEnclosure;

public interface JiaiEnclosureMapper {
    int insert(JiaiEnclosure record);

    int insertSelective(JiaiEnclosure record);

    JiaiEnclosure  findEnclosureNewInfo(String imei);

    int updateByPrimaryKeySelective(JiaiEnclosure record);

    int deljiaiEncloInfo(String imei);
}