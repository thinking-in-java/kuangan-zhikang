package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhSportViconWeb;

public interface YhSportViconWebMapper {
    int deleteByPrimaryKey(Integer viconId);

    int insert(YhSportViconWeb record);

    int insertSelective(YhSportViconWeb record);

    YhSportViconWeb selectByPrimaryKey(Integer viconId);

    int updateByPrimaryKeySelective(YhSportViconWeb record);

    int updateByPrimaryKey(YhSportViconWeb record);
    
    List<YhSportViconWeb> selectSportVicon(Map<String, Object> map);
    
    int selectSportViconCount(Map<String, Object> map);
}