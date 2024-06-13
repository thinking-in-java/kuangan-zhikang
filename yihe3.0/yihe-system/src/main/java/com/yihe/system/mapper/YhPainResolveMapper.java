package com.yihe.system.mapper;

import com.yihe.system.domain.YhPainResolve;
import com.yihe.system.domain.YhPainResolveWithBLOBs;

public interface YhPainResolveMapper {
    int deleteByPrimaryKey(Integer resolveId);

    int insert(YhPainResolveWithBLOBs record);

    int insertSelective(YhPainResolveWithBLOBs record);

    YhPainResolveWithBLOBs selectByPrimaryKey(Integer resolveId);

    int updateByPrimaryKeySelective(YhPainResolveWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(YhPainResolveWithBLOBs record);

    int updateByPrimaryKey(YhPainResolve record);
}