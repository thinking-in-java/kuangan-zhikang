package com.yihe.system.service;

import com.yihe.system.domain.NewCommodityData;

import java.util.List;

public interface NewCommodityDataService {
    NewCommodityData selectInvent(NewCommodityData newCommodityData);

    int insertOrUpdate(NewCommodityData newCommodityData);

    List<NewCommodityData> selectByIdAndCode(NewCommodityData newCommodityData);
}
