package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhWxInformation;

public interface YhWxInformationMapper {
    int deleteByPrimaryKey(Integer wxId);

    int insert(YhWxInformation record);

    int insertSelective(YhWxInformation record);

    YhWxInformation selectByPrimaryKey(Integer wxId);

    int updateByPrimaryKeySelective(YhWxInformation record);

    int updateByPrimaryKey(YhWxInformation record);
    
    List<YhWxInformation> selectbyWxINfo();
    
    YhWxInformation selectbyWxType(String wxType);
}