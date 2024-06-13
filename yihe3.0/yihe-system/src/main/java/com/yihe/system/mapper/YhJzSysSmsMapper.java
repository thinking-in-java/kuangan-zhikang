package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhJzSysSmsWeb;

public interface YhJzSysSmsMapper {
    int deleteByPrimaryKey(Long smsId);

    int insert(YhJzSysSmsWeb record);

    int insertSelective(YhJzSysSmsWeb record);

    YhJzSysSmsWeb selectByPrimaryKey(Long smsId);

    int updateByPrimaryKeySelective(YhJzSysSmsWeb record);

    int updateByPrimaryKey(YhJzSysSmsWeb record);
    
    List<YhJzSysSmsWeb> showSysSmsAll();
    
    List<YhJzSysSmsWeb> selectSmsNull();
    
    YhJzSysSmsWeb selectCountGenre(String templateGenre);
    
    YhJzSysSmsWeb selectYhJzSysSmslogistics();
}