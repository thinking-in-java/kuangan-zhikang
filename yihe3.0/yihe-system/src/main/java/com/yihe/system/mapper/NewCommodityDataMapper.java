package com.yihe.system.mapper;

import com.yihe.system.domain.NewCommodityData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewCommodityDataMapper {
    NewCommodityData selectInvent(NewCommodityData newCommodityData);

    int insert(NewCommodityData newCommodityData);

    int update(NewCommodityData newCommodityData);

    int deletByIdAndCode(@Param("orgCode") String orgCode,@Param("id") Integer id);

    List<NewCommodityData> selectByIdAndCode(NewCommodityData newCommodityData);
}
