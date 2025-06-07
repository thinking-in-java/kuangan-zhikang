package com.yihe.system.mapper;


import com.yihe.system.domain.NewOccupationalHealthManagementArchives;
import com.yihe.system.domain.NewUnitOccupationalHealthSurveillanceManagementArchives;

import java.util.List;

public interface NewUnitOccupationalHealthSurveillanceManagementArchivesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewUnitOccupationalHealthSurveillanceManagementArchives row);

    int insertSelective(NewUnitOccupationalHealthSurveillanceManagementArchives row);

    NewUnitOccupationalHealthSurveillanceManagementArchives selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewUnitOccupationalHealthSurveillanceManagementArchives row);

    int updateByPrimaryKeyWithBLOBs(NewUnitOccupationalHealthSurveillanceManagementArchives row);

    int updateByPrimaryKey(NewUnitOccupationalHealthSurveillanceManagementArchives row);

    List<NewUnitOccupationalHealthSurveillanceManagementArchives> selectByPage(NewUnitOccupationalHealthSurveillanceManagementArchives row);

    int selectByPageCount(NewUnitOccupationalHealthSurveillanceManagementArchives row);

    NewUnitOccupationalHealthSurveillanceManagementArchives selectOrder();
}