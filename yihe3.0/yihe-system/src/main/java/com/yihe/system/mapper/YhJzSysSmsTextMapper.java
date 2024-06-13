package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhJzSysSmsText;

public interface YhJzSysSmsTextMapper {
    int deleteByPrimaryKey(Integer tId);

    int insert(YhJzSysSmsText record);

    int insertSelective(YhJzSysSmsText record);

    YhJzSysSmsText selectByPrimaryKey(Integer tId);

    int updateByPrimaryKeySelective(YhJzSysSmsText record);

    int updateByPrimaryKey(YhJzSysSmsText record);
    
    List<YhJzSysSmsText> selectSmsId(Integer tSmsid);
}