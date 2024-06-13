package com.yihe.system.mapper;

import com.yihe.system.domain.JiaiInterfaceList;

public interface JiaiInterfaceListMapper {

    JiaiInterfaceList selectByPrimaryKeyByDescribe(String describe);

    int deleteByPrimaryKey(Integer jid);

    int insert(JiaiInterfaceList record);

    int insertSelective(JiaiInterfaceList record);

    JiaiInterfaceList selectByPrimaryKey(Integer jid);

    int updateByPrimaryKeySelective(JiaiInterfaceList record);

    int updateByPrimaryKey(JiaiInterfaceList record);
}