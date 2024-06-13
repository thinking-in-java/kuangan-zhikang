package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhOrgFacility;

public interface YhOrgFacilityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhOrgFacility record);

    int insertSelective(YhOrgFacility record);

    YhOrgFacility selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhOrgFacility record);

    int updateByPrimaryKey(YhOrgFacility record);
    
    YhOrgFacility showOrgCodeBydeviceid(String deviceid);
    
    List<YhOrgFacility> showOrgCodeList(String orgCode);
    
    int showOrgCodeListCount(String orgCode);
}