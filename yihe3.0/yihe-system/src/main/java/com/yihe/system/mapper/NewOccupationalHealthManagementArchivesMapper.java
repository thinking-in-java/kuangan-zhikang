package com.yihe.system.mapper;


import com.yihe.system.domain.NewOccupationalHealthManagementArchives;
import com.yihe.system.domain.NewOccupationalHealthPromotionTrainingArchive;

import java.util.List;

public interface NewOccupationalHealthManagementArchivesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewOccupationalHealthManagementArchives row);

    int insertSelective(NewOccupationalHealthManagementArchives row);

    NewOccupationalHealthManagementArchives selectOrder();

    NewOccupationalHealthManagementArchives selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewOccupationalHealthManagementArchives row);

    int updateByPrimaryKey(NewOccupationalHealthManagementArchives row);

    List<NewOccupationalHealthManagementArchives> selectByPage(NewOccupationalHealthManagementArchives row);

    int selectByPageCount(NewOccupationalHealthManagementArchives row);
}