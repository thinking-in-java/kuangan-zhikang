package com.yihe.system.service.impl;

import com.yihe.system.domain.NewOccupationalHealthManagementArchives;
import com.yihe.system.domain.NewThreeSimultaneous;
import com.yihe.system.mapper.NewOccupationalHealthManagementArchivesMapper;
import com.yihe.system.service.NewOccupationalHealthManagementArchivesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class NewOccupationalHealthManagementArchivesServiceImpl implements NewOccupationalHealthManagementArchivesService {
    @Resource
    NewOccupationalHealthManagementArchivesMapper newOccupationalHealthManagementArchivesMapper;
    @Override
    public int insetNew(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives) {
        int year = LocalDate.now().getYear();
        NewOccupationalHealthManagementArchives nowThree=newOccupationalHealthManagementArchivesMapper.selectOrder();
        if(nowThree!=null&&nowThree.getArchiveNumber()!=null && nowThree.getArchiveNumber().length()>13){
            String last=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 6);
            String nowYear=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 12,nowThree.getArchiveNumber().length() - 8);
            if(Integer.valueOf(nowYear).equals(year)){
                int incrementedValue = Integer.valueOf(last) + 1;
                String formattedValue = String.format("%07d", incrementedValue);
                newOccupationalHealthManagementArchives.setArchiveNumber("ZYWS—02—"+year+"—"+formattedValue);
            }else{
                newOccupationalHealthManagementArchives.setArchiveNumber("ZYWS—02—"+year+"—"+"0000001");
            }
        }else{

            newOccupationalHealthManagementArchives.setArchiveNumber("ZYWS—02—"+year+"—"+"0000001");
        }
        return newOccupationalHealthManagementArchivesMapper.insertSelective(newOccupationalHealthManagementArchives);
    }

    @Override
    public int updateNew(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives) {
        return newOccupationalHealthManagementArchivesMapper.updateByPrimaryKeySelective(newOccupationalHealthManagementArchives);
    }

    @Override
    public NewOccupationalHealthManagementArchives selectById(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives) {
        return newOccupationalHealthManagementArchivesMapper.selectByPrimaryKey(newOccupationalHealthManagementArchives.getId());
    }

    @Override
    public List<NewOccupationalHealthManagementArchives> selectByPage(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives) {
        return newOccupationalHealthManagementArchivesMapper.selectByPage(newOccupationalHealthManagementArchives);
    }

    @Override
    public int selectByPageCount(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives) {
        return newOccupationalHealthManagementArchivesMapper.selectByPageCount(newOccupationalHealthManagementArchives);
    }

    @Override
    public int deleteById(NewOccupationalHealthManagementArchives newOccupationalHealthManagementArchives) {
        return newOccupationalHealthManagementArchivesMapper.deleteByPrimaryKey(newOccupationalHealthManagementArchives.getId());
    }
}
