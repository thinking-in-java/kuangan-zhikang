package com.yihe.system.service.impl;

import com.yihe.system.domain.NewMonitoringOccupationalHazardFactors;
import com.yihe.system.domain.NewOccupationalHealthManagementArchives;
import com.yihe.system.domain.NewOccupationalHealthPromotionTrainingArchive;
import com.yihe.system.mapper.NewOccupationalHealthPromotionTrainingArchiveMapper;
import com.yihe.system.service.NewOccupationalHealthPromotionTrainingArchiveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class NewOccupationalHealthPromotionTrainingArchiveServiceImpl implements NewOccupationalHealthPromotionTrainingArchiveService {
    @Resource
    NewOccupationalHealthPromotionTrainingArchiveMapper newOccupationalHealthPromotionTrainingArchiveMapper;
    @Override
    public int insetNew(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        int year = LocalDate.now().getYear();
        NewOccupationalHealthPromotionTrainingArchive nowThree=newOccupationalHealthPromotionTrainingArchiveMapper.selectOrder();
        if(nowThree!=null&&nowThree.getArchiveNumber()!=null && nowThree.getArchiveNumber().length()>13){
            String last=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 6);
            String nowYear=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 12,nowThree.getArchiveNumber().length() - 8);
            if(Integer.valueOf(nowYear).equals(year)){
                int incrementedValue = Integer.valueOf(last) + 1;
                String formattedValue = String.format("%07d", incrementedValue);
                newOccupationalHealthPromotionTrainingArchive.setArchiveNumber("ZYWS—02—"+year+"—"+formattedValue);
            }else{
                newOccupationalHealthPromotionTrainingArchive.setArchiveNumber("ZYWS—02—"+year+"—"+"0000001");
            }
        }else{

            newOccupationalHealthPromotionTrainingArchive.setArchiveNumber("ZYWS—02—"+year+"—"+"0000001");
        }
        return newOccupationalHealthPromotionTrainingArchiveMapper.insertSelective(newOccupationalHealthPromotionTrainingArchive);
    }

    @Override
    public int updateNew(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        return newOccupationalHealthPromotionTrainingArchiveMapper.updateByPrimaryKeySelective(newOccupationalHealthPromotionTrainingArchive);
    }

    @Override
    public NewOccupationalHealthPromotionTrainingArchive selectById(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        return newOccupationalHealthPromotionTrainingArchiveMapper.selectByPrimaryKey(newOccupationalHealthPromotionTrainingArchive.getId());
    }

    @Override
    public List<NewOccupationalHealthPromotionTrainingArchive> selectByPage(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        return newOccupationalHealthPromotionTrainingArchiveMapper.selectByPage(newOccupationalHealthPromotionTrainingArchive);
    }

    @Override
    public int selectByPageCount(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        return newOccupationalHealthPromotionTrainingArchiveMapper.selectByPageCount(newOccupationalHealthPromotionTrainingArchive);
    }

    @Override
    public int deleteById(NewOccupationalHealthPromotionTrainingArchive newOccupationalHealthPromotionTrainingArchive) {
        return newOccupationalHealthPromotionTrainingArchiveMapper.deleteByPrimaryKey(newOccupationalHealthPromotionTrainingArchive.getId());
    }
}
