package com.yihe.system.service.impl;

import com.yihe.system.domain.NewMedicalExaminationRecords;
import com.yihe.system.mapper.NewMedicalExaminationRecordsMapper;
import com.yihe.system.service.NewMedicalExaminationRecordsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewMedicalExaminationRecordsServiceImpl implements NewMedicalExaminationRecordsService {
    @Resource
    NewMedicalExaminationRecordsMapper newMedicalExaminationRecordsMapper;
    @Override
    public List<NewMedicalExaminationRecords> selectByPage(NewMedicalExaminationRecords newMedicalExaminationRecords, Integer page, Integer limit) {
        int begin=(page-1)*limit;
        newMedicalExaminationRecords.setBegin(begin);
        newMedicalExaminationRecords.setLimit(limit);
        return newMedicalExaminationRecordsMapper.selectByPage(newMedicalExaminationRecords);
    }

    @Override
    public int selectByPageCount(NewMedicalExaminationRecords newMedicalExaminationRecords) {
        return newMedicalExaminationRecordsMapper.selectByPageCount(newMedicalExaminationRecords);
    }

    @Override
    public NewMedicalExaminationRecords selectById(NewMedicalExaminationRecords newMedicalExaminationRecords) {
        return newMedicalExaminationRecordsMapper.selectByPrimaryKeyId(newMedicalExaminationRecords.getId());
    }

    @Override
    public int insert(NewMedicalExaminationRecords newMedicalExaminationRecords) {
        return newMedicalExaminationRecordsMapper.insertSelective(newMedicalExaminationRecords);
    }

    @Override
    public int updateById(NewMedicalExaminationRecords newMedicalExaminationRecords) {
        return newMedicalExaminationRecordsMapper.updateByPrimaryKeySelective(newMedicalExaminationRecords);
    }

    @Override
    public int deleteById(NewMedicalExaminationRecords newMedicalExaminationRecords) {
        return newMedicalExaminationRecordsMapper.deleteByPrimaryKey(newMedicalExaminationRecords.getId());
    }
}
