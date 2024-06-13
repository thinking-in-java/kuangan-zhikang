package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserShippingAddressWeb;

public interface YhUserShippingAddressWebMapper {
    int deleteByPrimaryKey(Integer siteId);

    int insert(YhUserShippingAddressWeb record);

    int insertSelective(YhUserShippingAddressWeb record);

    YhUserShippingAddressWeb selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(YhUserShippingAddressWeb record);

    int updateByPrimaryKey(YhUserShippingAddressWeb record);
}