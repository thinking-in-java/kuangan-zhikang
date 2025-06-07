package com.yihe.system.service.impl;

import com.yihe.system.domain.NewPneumoconiosisServiceRecordForm;
import com.yihe.system.domain.newRegWithBLOBs;
import com.yihe.system.mapper.NewPneumoconiosisServiceRecordFormMapper;
import com.yihe.system.mapper.newRegMapper;
import com.yihe.system.service.NewPneumoconiosisServiceRecordFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewPneumoconiosisServiceRecordFormServiceImpl implements NewPneumoconiosisServiceRecordFormService {
    @Resource
    NewPneumoconiosisServiceRecordFormMapper newPneumoconiosisServiceRecordFormMapper;
    @Resource
    com.yihe.system.mapper.newRegMapper newRegMapper;

    @Override
    public int insetNew(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm) {
        newRegWithBLOBs newRegWithBLOBs=new newRegWithBLOBs();
        newRegWithBLOBs.setId(newPneumoconiosisServiceRecordForm.getRegId());
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newRegWithBLOBs.setInterventionTime(dfs.format(new Date()));
        newRegMapper.updateByPrimaryKeySelective(newRegWithBLOBs);
        return newPneumoconiosisServiceRecordFormMapper.insertSelective(newPneumoconiosisServiceRecordForm);
    }

    @Override
    public int updateNew(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm) {
        return newPneumoconiosisServiceRecordFormMapper.updateByPrimaryKeySelective(newPneumoconiosisServiceRecordForm);
    }

    @Override
    public NewPneumoconiosisServiceRecordForm selectById(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm) {
        return newPneumoconiosisServiceRecordFormMapper.selectByPrimaryKey(newPneumoconiosisServiceRecordForm.getId());
    }

    @Override
    public List<NewPneumoconiosisServiceRecordForm> selectByPage(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm) {
        return newPneumoconiosisServiceRecordFormMapper.selectByPage(newPneumoconiosisServiceRecordForm);
    }

    @Override
    public int selectByPageCount(NewPneumoconiosisServiceRecordForm newPneumoconiosisServiceRecordForm) {
        return newPneumoconiosisServiceRecordFormMapper.selectByPageCount(newPneumoconiosisServiceRecordForm);
    }

    @Override
    public List<NewPneumoconiosisServiceRecordForm> selectAll() {
        return newPneumoconiosisServiceRecordFormMapper.selectAll();
    }
}
