package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserShippingAddressApp;
 
public interface YhUserShippingAddressAppMapper {
    int deleteByPrimaryKey(Integer siteId);

    int insert(YhUserShippingAddressApp record);

    int insertSelective(YhUserShippingAddressApp record);

    YhUserShippingAddressApp selectByPrimaryKey(Integer siteId);
    
    YhUserShippingAddressApp selectNewestShippingAddress(String  userCode);

    int updateByPrimaryKeySelective(YhUserShippingAddressApp record);

    int updateByPrimaryKey(YhUserShippingAddressApp record);
    int updateUserAddressStatus(String userCode);
    List<YhUserShippingAddressApp> selectAllShippingAddress(String userCode);
}