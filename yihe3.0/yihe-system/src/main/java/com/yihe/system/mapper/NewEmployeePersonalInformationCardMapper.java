package com.yihe.system.mapper;


import com.yihe.system.domain.NewEmployeePersonalInformationCard;
import com.yihe.system.domain.NewInterventionRecordFormForHighRiskPopulation;

import java.util.List;

public interface NewEmployeePersonalInformationCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewEmployeePersonalInformationCard row);

    int insertSelective(NewEmployeePersonalInformationCard row);

    NewEmployeePersonalInformationCard selectByPrimaryKey(Integer id);

    List<NewEmployeePersonalInformationCard> selectByUser(String userCode);

    Integer selectId(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);

    Integer inserCount(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);

    int updateByPrimaryKeySelective(NewEmployeePersonalInformationCard row);

    int updateByPrimaryKey(NewEmployeePersonalInformationCard row);

    List<NewEmployeePersonalInformationCard> selectByPage(NewEmployeePersonalInformationCard row);

    int selectByPageCount(NewEmployeePersonalInformationCard row);
}