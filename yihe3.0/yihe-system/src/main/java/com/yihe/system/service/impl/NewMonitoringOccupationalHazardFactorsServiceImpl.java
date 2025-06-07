package com.yihe.system.service.impl;

import com.yihe.system.domain.NewMonitoringOccupationalHazardFactors;
import com.yihe.system.domain.NewOccupationalHealthManagementArchives;
import com.yihe.system.mapper.NewMonitoringOccupationalHazardFactorsMapper;
import com.yihe.system.service.NewMonitoringOccupationalHazardFactorsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class NewMonitoringOccupationalHazardFactorsServiceImpl implements NewMonitoringOccupationalHazardFactorsService {
    @Resource
    NewMonitoringOccupationalHazardFactorsMapper newMonitoringOccupationalHazardFactorsMapper;

    @Override
    public int insetNew(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors) {
        int year = LocalDate.now().getYear();
        NewMonitoringOccupationalHazardFactors nowThree=newMonitoringOccupationalHazardFactorsMapper.selectOrder();
        if(nowThree!=null&&nowThree.getArchiveNumber()!=null && nowThree.getArchiveNumber().length()>13){
            String last=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 6);
            String nowYear=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 12,nowThree.getArchiveNumber().length() - 8);
            if(Integer.valueOf(nowYear).equals(year)){
                int incrementedValue = Integer.valueOf(last) + 1;
                String formattedValue = String.format("%07d", incrementedValue);
                newMonitoringOccupationalHazardFactors.setArchiveNumber("ZYWS—04—"+year+"—"+formattedValue);
            }else{
                newMonitoringOccupationalHazardFactors.setArchiveNumber("ZYWS—04—"+year+"—"+"0000001");
            }
        }else{
            newMonitoringOccupationalHazardFactors.setArchiveNumber("ZYWS—04—"+year+"—"+"0000001");
        }
        return newMonitoringOccupationalHazardFactorsMapper.insertSelective(newMonitoringOccupationalHazardFactors);
    }

    @Override
    public int updateNew(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors) {
        return newMonitoringOccupationalHazardFactorsMapper.updateByPrimaryKeySelective(newMonitoringOccupationalHazardFactors);
    }

    @Override
    public NewMonitoringOccupationalHazardFactors selectById(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors) {
        return newMonitoringOccupationalHazardFactorsMapper.selectByPrimaryKey(newMonitoringOccupationalHazardFactors.getId());
    }

    @Override
    public List<NewMonitoringOccupationalHazardFactors> selectByPage(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors) {
        return newMonitoringOccupationalHazardFactorsMapper.selectByPage(newMonitoringOccupationalHazardFactors);
    }

    @Override
    public int selectByPageCount(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors) {
        return newMonitoringOccupationalHazardFactorsMapper.selectByPageCount(newMonitoringOccupationalHazardFactors);
    }

    @Override
    public int deleteById(NewMonitoringOccupationalHazardFactors newMonitoringOccupationalHazardFactors) {
        return newMonitoringOccupationalHazardFactorsMapper.deleteByPrimaryKey(newMonitoringOccupationalHazardFactors.getId());
    }
}
