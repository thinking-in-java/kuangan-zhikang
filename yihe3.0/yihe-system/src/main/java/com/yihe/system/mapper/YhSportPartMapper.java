package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhSportPart;

public interface YhSportPartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhSportPart record);

    int insertSelective(YhSportPart record);

    YhSportPart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportPart record);

    int updateByPrimaryKey(YhSportPart record);
    
    List<YhSportPart> showSportPart();
}