package com.yihe.system.mapper;

import com.yihe.system.domain.JiaiWatchInfo;

public interface JiaiWatchInfoMapper {
    int deleteByPrimaryKey(String terminalid);

    int insert(JiaiWatchInfo record);

    int insertSelective(JiaiWatchInfo record);

    JiaiWatchInfo selectByPrimaryKey(String terminalid);

    int updateByPrimaryKeySelective(JiaiWatchInfo record);

    int updateByPrimaryKey(JiaiWatchInfo record);
}