package com.yihe.system.mapper;


import com.yihe.system.domain.NewAnnualOccupationalHealthPromotionTrainingList;
import com.yihe.system.domain.NewOccupationalHealthManagementArchives;

import java.util.List;

public interface NewAnnualOccupationalHealthPromotionTrainingListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewAnnualOccupationalHealthPromotionTrainingList row);

    int insertSelective(NewAnnualOccupationalHealthPromotionTrainingList row);

    NewAnnualOccupationalHealthPromotionTrainingList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewAnnualOccupationalHealthPromotionTrainingList row);

    int updateByPrimaryKeyWithBLOBs(NewAnnualOccupationalHealthPromotionTrainingList row);

    int updateByPrimaryKey(NewAnnualOccupationalHealthPromotionTrainingList row);


    List<NewAnnualOccupationalHealthPromotionTrainingList> selectByPage(NewAnnualOccupationalHealthPromotionTrainingList row);

    NewAnnualOccupationalHealthPromotionTrainingList selectOrder();

    int selectByPageCount(NewAnnualOccupationalHealthPromotionTrainingList row);
}