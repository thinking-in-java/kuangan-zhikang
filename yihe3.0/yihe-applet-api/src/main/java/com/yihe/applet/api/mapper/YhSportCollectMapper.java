package com.yihe.applet.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhSportCollect;


public interface YhSportCollectMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(YhSportCollect record);

    int insertSelective(YhSportCollect record);

    YhSportCollect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportCollect record);

    int updateByPrimaryKey(YhSportCollect record);
    
    int showCollectRepetition(Map<String, Object> map);
    
    int deleteByPrimaryKeymap(Map<String, Object> map);
}