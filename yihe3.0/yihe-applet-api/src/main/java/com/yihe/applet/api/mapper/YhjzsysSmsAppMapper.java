package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhJzSysSmsApp;

import java.util.List;

import org.apache.ibatis.annotations.Param;
public interface YhjzsysSmsAppMapper {
    int deleteByPrimaryKey(Long smsId);

    int insert(YhJzSysSmsApp record);

    int insertSelective(YhJzSysSmsApp record);

    YhJzSysSmsApp selectByPrimaryKey(Long smsId);

    List<YhJzSysSmsApp> selectYhJzSysSmsAppBytype();
    
    int updateByPrimaryKeySelective(YhJzSysSmsApp record);

    int updateByPrimaryKey(YhJzSysSmsApp record);
    YhJzSysSmsApp selectById(@Param("smsId") Integer smsId);
}