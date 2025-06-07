package com.yihe.system.service.impl;

import com.yihe.system.domain.NewThreeSimultaneous;
import com.yihe.system.mapper.NewThreeSimultaneousMapper;
import com.yihe.system.service.NewThreeSimultaneousService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class NewThreeSimultaneousServiceImpl implements NewThreeSimultaneousService {
    @Resource
    NewThreeSimultaneousMapper newThreeSimultaneousMapper;
    @Override
    public int insetNew(NewThreeSimultaneous newThreeSimultaneous) {
        int year = LocalDate.now().getYear();
        NewThreeSimultaneous nowThree=newThreeSimultaneousMapper.selectOrder();
        if(nowThree!=null&&nowThree.getArchiveNumber()!=null && nowThree.getArchiveNumber().length()>13){
            String last=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 6);
            String nowYear=nowThree.getArchiveNumber().substring(nowThree.getArchiveNumber().length() - 12,nowThree.getArchiveNumber().length() - 8);
            if(Integer.valueOf(nowYear).equals(year)){
                int incrementedValue = Integer.valueOf(last) + 1;
                String formattedValue = String.format("%07d", incrementedValue);
                newThreeSimultaneous.setArchiveNumber("ZYWS—01—"+year+"—"+formattedValue);
            }else{
                newThreeSimultaneous.setArchiveNumber("ZYWS—01—"+year+"—"+"0000001");
            }
        }else{

            newThreeSimultaneous.setArchiveNumber("ZYWS—01—"+year+"—"+"0000001");
        }

        return newThreeSimultaneousMapper.insertSelective(newThreeSimultaneous);
    }

    @Override
    public int updateNew(NewThreeSimultaneous newThreeSimultaneous) {
        return newThreeSimultaneousMapper.updateByPrimaryKeySelective(newThreeSimultaneous);
    }

    @Override
    public NewThreeSimultaneous selectById(NewThreeSimultaneous newThreeSimultaneous) {
        return newThreeSimultaneousMapper.selectByPrimaryKey(newThreeSimultaneous.getId());
    }

    @Override
    public List<NewThreeSimultaneous> selectByPage(NewThreeSimultaneous newThreeSimultaneous) {
        return newThreeSimultaneousMapper.selectByPage(newThreeSimultaneous);
    }

    @Override
    public int selectByPageCount(NewThreeSimultaneous newThreeSimultaneous) {
        return newThreeSimultaneousMapper.selectByPageCount(newThreeSimultaneous);
    }

    @Override
    public int deleteById(NewThreeSimultaneous newThreeSimultaneous) {
        return newThreeSimultaneousMapper.deleteByPrimaryKey(newThreeSimultaneous.getId());
    }
}
