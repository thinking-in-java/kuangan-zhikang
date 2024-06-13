package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhOrgTreeApp;

public interface YhOrgTreeAppMapper {
    int deleteByPrimaryKey(String orgCode);

    int insert(YhOrgTreeApp record);

    int insertSelective(YhOrgTreeApp record);

    YhOrgTreeApp selectByPrimaryKey(String orgCode);

    int updateByPrimaryKeySelective(YhOrgTreeApp record);

    int updateByPrimaryKeyWithBLOBs(YhOrgTreeApp record);

    int updateByPrimaryKey(YhOrgTreeApp record);
}