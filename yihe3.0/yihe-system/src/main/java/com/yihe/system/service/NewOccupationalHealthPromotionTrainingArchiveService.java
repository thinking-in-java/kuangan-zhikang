package com.yihe.system.service;

import com.yihe.system.domain.NewMonitoringOccupationalHazardFactors;
import com.yihe.system.domain.NewOccupationalHealthPromotionTrainingArchive;

import java.util.List;

public interface NewOccupationalHealthPromotionTrainingArchiveService {
    int insetNew(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) ;

    int updateNew(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) ;

    NewOccupationalHealthPromotionTrainingArchive selectById(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) ;

    List<NewOccupationalHealthPromotionTrainingArchive> selectByPage(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) ;

    int selectByPageCount(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) ;

    int deleteById(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) ;
}
