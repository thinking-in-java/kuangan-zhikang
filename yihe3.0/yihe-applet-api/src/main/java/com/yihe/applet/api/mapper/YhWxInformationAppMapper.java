package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhWxInformationApp;

public interface YhWxInformationAppMapper {
    int deleteByPrimaryKey(Integer wxId);

    int insert(YhWxInformationApp record); 

    int insertSelective(YhWxInformationApp record);

    YhWxInformationApp selectByPrimaryKey(Integer wxId);

    int updateByPrimaryKeySelective(YhWxInformationApp record);

    int updateByPrimaryKey(YhWxInformationApp record);
      
    List<YhWxInformationApp> selectAll(); 
}