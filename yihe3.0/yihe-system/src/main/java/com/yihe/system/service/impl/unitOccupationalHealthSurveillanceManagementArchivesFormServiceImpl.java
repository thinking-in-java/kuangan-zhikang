package com.yihe.system.service.impl;

import com.yihe.system.domain.NewOccupationalHealthManagementArchives;
import com.yihe.system.domain.NewUnitOccupationalHealthSurveillanceManagementArchives;
import com.yihe.system.mapper.NewOccupationalHealthManagementArchivesMapper;
import com.yihe.system.mapper.NewUnitOccupationalHealthSurveillanceManagementArchivesMapper;
import com.yihe.system.service.unitOccupationalHealthSurveillanceManagementArchivesFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class unitOccupationalHealthSurveillanceManagementArchivesFormServiceImpl implements unitOccupationalHealthSurveillanceManagementArchivesFormService {
    @Resource
    NewUnitOccupationalHealthSurveillanceManagementArchivesMapper newUnitOccupationalHealthSurveillanceManagementArchivesMapper;
    @Override
    public int insetNew(NewUnitOccupationalHealthSurveillanceManagementArchives newOccupationalHealthManagementArchives) {
        int year = LocalDate.now().getYear();
        NewUnitOccupationalHealthSurveillanceManagementArchives nowThree=newUnitOccupationalHealthSurveillanceManagementArchivesMapper.selectOrder();
        if(nowThree!=null&&nowThree.getArchiveNumber()!=null && nowThree.getArchiveNumber().length()>13){
            String last=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 6);
            String nowYear=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 12,nowThree.getArchiveNumber().length() - 8);
            if(Integer.valueOf(nowYear).equals(year)){
                int incrementedValue = Integer.valueOf(last) + 1;
                String formattedValue = String.format("%07d", incrementedValue);
                newOccupationalHealthManagementArchives.setArchiveNumber("ZYWS—05—"+year+"—"+formattedValue);
            }else{
                newOccupationalHealthManagementArchives.setArchiveNumber("ZYWS—05—"+year+"—"+"0000001");
            }
        }else{

            newOccupationalHealthManagementArchives.setArchiveNumber("ZYWS—05—"+year+"—"+"0000001");
        }
        return newUnitOccupationalHealthSurveillanceManagementArchivesMapper.insertSelective(newOccupationalHealthManagementArchives);
    }

    @Override
    public int updateNew(NewUnitOccupationalHealthSurveillanceManagementArchives newOccupationalHealthManagementArchives) {
        return newUnitOccupationalHealthSurveillanceManagementArchivesMapper.updateByPrimaryKeySelective(newOccupationalHealthManagementArchives);
    }

    @Override
    public NewUnitOccupationalHealthSurveillanceManagementArchives selectById(NewUnitOccupationalHealthSurveillanceManagementArchives newOccupationalHealthManagementArchives) {
        return newUnitOccupationalHealthSurveillanceManagementArchivesMapper.selectByPrimaryKey(newOccupationalHealthManagementArchives.getId());
    }

    @Override
    public List<NewUnitOccupationalHealthSurveillanceManagementArchives> selectByPage(NewUnitOccupationalHealthSurveillanceManagementArchives newOccupationalHealthManagementArchives) {
        return newUnitOccupationalHealthSurveillanceManagementArchivesMapper.selectByPage(newOccupationalHealthManagementArchives);
    }

    @Override
    public int selectByPageCount(NewUnitOccupationalHealthSurveillanceManagementArchives newOccupationalHealthManagementArchives) {
        return newUnitOccupationalHealthSurveillanceManagementArchivesMapper.selectByPageCount(newOccupationalHealthManagementArchives);
    }

    @Override
    public int deleteById(NewUnitOccupationalHealthSurveillanceManagementArchives newUnitOccupationalHealthSurveillanceManagementArchives) {
        return newUnitOccupationalHealthSurveillanceManagementArchivesMapper.deleteByPrimaryKey(newUnitOccupationalHealthSurveillanceManagementArchives.getId());
    }
}
