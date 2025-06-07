package com.yihe.system.service;

import com.yihe.system.domain.NewObsessiveCompulsiveDisorderInterventionRecordForm;
import com.yihe.system.domain.NewOccupationalHealthManagementArchives;

import java.util.List;

public interface NewOccupationalHealthManagementArchivesService {
    int insetNew(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives);

    int updateNew(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives);

    NewOccupationalHealthManagementArchives selectById(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives);

    List<NewOccupationalHealthManagementArchives> selectByPage(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives);

    int selectByPageCount(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives);

    int deleteById(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives);
}
