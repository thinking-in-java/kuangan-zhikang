package com.yihe.applet.api.mapper;

import java.util.Map;

import com.yihe.applet.api.domain.YhEventRegisterApp;

public interface YhEventRegisterAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhEventRegisterApp record);

    int insertSelective(YhEventRegisterApp record);

    YhEventRegisterApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhEventRegisterApp record);

    int updateByPrimaryKey(YhEventRegisterApp record);
    
    int showRegisterCount(Map<String, Object> map);
}