package com.yihe.system.service.impl;

import com.yihe.system.domain.NewAnnualOccupationalHealthPromotionTrainingList;
import com.yihe.system.domain.NewOccupationalHealthManagementArchives;
import com.yihe.system.mapper.NewAnnualOccupationalHealthPromotionTrainingListMapper;
import com.yihe.system.service.NewAnnualOccupationalHealthPromotionTrainingListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class NewAnnualOccupationalHealthPromotionTrainingListServiceImpl implements NewAnnualOccupationalHealthPromotionTrainingListService {

    @Resource
    NewAnnualOccupationalHealthPromotionTrainingListMapper newAnnualOccupationalHealthPromotionTrainingListMapper;
    @Override
    public int insetNew(NewAnnualOccupationalHealthPromotionTrainingList newAnnualOccupationalHealthPromotionTrainingList) {

        int year = LocalDate.now().getYear();
        NewAnnualOccupationalHealthPromotionTrainingList nowThree=newAnnualOccupationalHealthPromotionTrainingListMapper.selectOrder();
        if(nowThree!=null&&nowThree.getArchiveNumber()!=null && nowThree.getArchiveNumber().length()>13){
            String last=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 6);
            String nowYear=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 12,nowThree.getArchiveNumber().length() - 8);
            if(Integer.valueOf(nowYear).equals(year)){
                int incrementedValue = Integer.valueOf(last) + 1;
                String formattedValue = String.format("%07d", incrementedValue);
                newAnnualOccupationalHealthPromotionTrainingList.setArchiveNumber("ZYWS—03—"+year+"—"+formattedValue);
            }else{
                newAnnualOccupationalHealthPromotionTrainingList.setArchiveNumber("ZYWS—03—"+year+"—"+"0000001");
            }
        }else{

            newAnnualOccupationalHealthPromotionTrainingList.setArchiveNumber("ZYWS—03—"+year+"—"+"0000001");
        }
        return newAnnualOccupationalHealthPromotionTrainingListMapper.insertSelective(newAnnualOccupationalHealthPromotionTrainingList);
    }

    @Override
    public int updateNew(NewAnnualOccupationalHealthPromotionTrainingList newAnnualOccupationalHealthPromotionTrainingList) {
        return newAnnualOccupationalHealthPromotionTrainingListMapper.updateByPrimaryKey(newAnnualOccupationalHealthPromotionTrainingList);
    }

    @Override
    public NewAnnualOccupationalHealthPromotionTrainingList selectById(NewAnnualOccupationalHealthPromotionTrainingList newAnnualOccupationalHealthPromotionTrainingList) {
        return newAnnualOccupationalHealthPromotionTrainingListMapper.selectByPrimaryKey(newAnnualOccupationalHealthPromotionTrainingList.getId());
    }

    @Override
    public List<NewAnnualOccupationalHealthPromotionTrainingList> selectByPage(NewAnnualOccupationalHealthPromotionTrainingList newAnnualOccupationalHealthPromotionTrainingList) {
        return newAnnualOccupationalHealthPromotionTrainingListMapper.selectByPage(newAnnualOccupationalHealthPromotionTrainingList);
    }

    @Override
    public int selectByPageCount(NewAnnualOccupationalHealthPromotionTrainingList newAnnualOccupationalHealthPromotionTrainingList) {
        return newAnnualOccupationalHealthPromotionTrainingListMapper.selectByPageCount(newAnnualOccupationalHealthPromotionTrainingList);
    }

    @Override
    public int deleteById(NewAnnualOccupationalHealthPromotionTrainingList newAnnualOccupationalHealthPromotionTrainingList) {
        return newAnnualOccupationalHealthPromotionTrainingListMapper.deleteByPrimaryKey(newAnnualOccupationalHealthPromotionTrainingList.getId());
    }
}
