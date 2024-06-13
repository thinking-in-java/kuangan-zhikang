package com.yihe.system.service;

import com.yihe.system.domain.TOnemachineData;

import java.util.List;

public interface TOnemachineDataService {
    int deleteByPrimaryKey(Long id);

    int insert(TOnemachineData record);

    int insertSelective(TOnemachineData record);

    TOnemachineData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TOnemachineData record);

    int updateByPrimaryKey(TOnemachineData record);

    List<TOnemachineData> queryOnemachine(String userCode);
}
