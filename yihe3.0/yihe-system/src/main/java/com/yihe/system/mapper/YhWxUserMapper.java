package com.yihe.system.mapper;

import com.yihe.system.domain.YhWxUserWeb;

public interface YhWxUserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(YhWxUserWeb record);

    int insertSelective(YhWxUserWeb record);

    YhWxUserWeb selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(YhWxUserWeb record);

    int updateByPrimaryKey(YhWxUserWeb record);
    
    YhWxUserWeb showRegInfoByWx(String oppenId);
}