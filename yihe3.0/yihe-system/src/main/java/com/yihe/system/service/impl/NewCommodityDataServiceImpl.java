package com.yihe.system.service.impl;

import com.yihe.system.domain.NewCommodityData;
import com.yihe.system.mapper.NewCommodityDataMapper;
import com.yihe.system.service.NewCommodityDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewCommodityDataServiceImpl implements NewCommodityDataService {

    @Resource
    NewCommodityDataMapper newCommodityDataMapper;
    @Override
    public NewCommodityData selectInvent(NewCommodityData newCommodityData) {
        String nowOrgCode=newCommodityData.getOrgCode();

        NewCommodityData newDate= newCommodityDataMapper.selectInvent(newCommodityData);

        while (nowOrgCode.length() >= 5 && newDate==null) {
            nowOrgCode =nowOrgCode.substring(0, nowOrgCode.length() - 5);
            newCommodityData.setOrgCode(nowOrgCode);
            newDate= newCommodityDataMapper.selectInvent(newCommodityData);
        }
        if(newDate==null){
            newDate=new NewCommodityData();
            newDate.setInventory(200);
        }

        return newDate;
    }

    @Override
    public int insertOrUpdate(NewCommodityData newCommodityData) {
        NewCommodityData newDate= newCommodityDataMapper.selectInvent(newCommodityData);
        int count=0;
        if(newDate==null){
            count=newCommodityDataMapper.insert(newCommodityData);
        }else{
             newCommodityData.setId(newDate.getId());
            count=newCommodityDataMapper.update(newCommodityData);
        }

        return count;
    }

    @Override
    public List<NewCommodityData> selectByIdAndCode(NewCommodityData newCommodityData) {
        return newCommodityDataMapper.selectByIdAndCode(newCommodityData);
    }
}
