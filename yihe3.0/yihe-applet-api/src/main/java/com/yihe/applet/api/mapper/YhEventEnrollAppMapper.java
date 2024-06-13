package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhEventEnrollApp;

import java.util.List;
import java.util.Map;

public interface YhEventEnrollAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhEventEnrollApp record);

    int insertSelective(YhEventEnrollApp record);

    YhEventEnrollApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhEventEnrollApp record);

    int updateByPrimaryKey(YhEventEnrollApp record);
    
    List<YhEventEnrollApp> selecyByEnroll(Map<String, Object> map);
}