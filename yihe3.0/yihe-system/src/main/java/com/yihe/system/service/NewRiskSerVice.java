package com.yihe.system.service;

import com.yihe.system.domain.NewRisk;
import com.yihe.system.domain.NewThreeSimultaneous;

import java.util.List;

public interface NewRiskSerVice {

    int insetNew(NewRisk newRisk);

    int updateNew(NewRisk newRisk);

    NewRisk selectById(NewRisk newRisk);

    List<NewRisk> selectByPage(NewRisk newRisk);

    int selectByPageCount(NewRisk newRisk);
}
