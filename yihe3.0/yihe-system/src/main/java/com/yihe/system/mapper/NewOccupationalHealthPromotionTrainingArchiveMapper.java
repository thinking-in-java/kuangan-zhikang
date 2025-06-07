package com.yihe.system.mapper;


import com.yihe.system.domain.NewMonitoringOccupationalHazardFactors;
import com.yihe.system.domain.NewOccupationalHealthManagementArchives;
import com.yihe.system.domain.NewOccupationalHealthPromotionTrainingArchive;

import java.util.List;

public interface NewOccupationalHealthPromotionTrainingArchiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewOccupationalHealthPromotionTrainingArchive row);

    int insertSelective(NewOccupationalHealthPromotionTrainingArchive row);

    NewOccupationalHealthPromotionTrainingArchive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewOccupationalHealthPromotionTrainingArchive row);

    int updateByPrimaryKeyWithBLOBs(NewOccupationalHealthPromotionTrainingArchive row);

    int updateByPrimaryKey(NewOccupationalHealthPromotionTrainingArchive row);

    List<NewOccupationalHealthPromotionTrainingArchive> selectByPage(NewOccupationalHealthPromotionTrainingArchive row);

    int selectByPageCount(NewOccupationalHealthPromotionTrainingArchive row);



    NewOccupationalHealthPromotionTrainingArchive selectOrder();
}