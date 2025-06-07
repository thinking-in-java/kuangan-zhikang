package com.yihe.system.service;

import com.yihe.system.domain.NewEmployeePersonalInformationCard;

import java.util.List;

public interface NewEmployeePersonalInformationCardService {

    List<NewEmployeePersonalInformationCard> selectByPage(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);

    int selectByPageCount(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);

    NewEmployeePersonalInformationCard selectById(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);

    Integer selectId(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);

    Integer inserCount(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);

    int insetNew(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);

    int updateNew(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);

    int deleteById(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard);
}
