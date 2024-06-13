package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhTemplateType;

public interface YhTemplateTypeMapper {
    int deleteByPrimaryKey(Integer tId);

    int insert(YhTemplateType record);

    int insertSelective(YhTemplateType record);

    YhTemplateType selectByPrimaryKey(Integer tId);

    int updateByPrimaryKeySelective(YhTemplateType record);

    int updateByPrimaryKey(YhTemplateType record);
    
    List<YhTemplateType> showTTypeAll(Integer tType);
}