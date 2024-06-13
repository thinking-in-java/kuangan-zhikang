package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserShippingAddressApp;

public interface YhUserShippingAddressAppService {
	 int deleteByPrimaryKey(Integer siteId);
	 int insertSelective(YhUserShippingAddressApp record);
	 YhUserShippingAddressApp selectByPrimaryKey(Integer siteId);
	 int updateByPrimaryKeySelective(YhUserShippingAddressApp record);
	 YhUserShippingAddressApp selectNewestShippingAddress(String  userCode);
	 List<YhUserShippingAddressApp> selectAllShippingAddress(String userCode);
	 int updateUserAddressStatus(String userCode);
}
