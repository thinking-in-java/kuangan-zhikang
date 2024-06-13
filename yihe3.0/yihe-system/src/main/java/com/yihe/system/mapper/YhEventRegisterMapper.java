package com.yihe.system.mapper;

import java.util.List;

import com.yihe.system.domain.YhEventRegister;

public interface YhEventRegisterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhEventRegister record);

    int insertSelective(YhEventRegister record);

    YhEventRegister selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhEventRegister record);

    int updateByPrimaryKey(YhEventRegister record);
    
    List<YhEventRegister> showActivityExport(int eventId);
    
    int showCountEvent(int eventId);
}