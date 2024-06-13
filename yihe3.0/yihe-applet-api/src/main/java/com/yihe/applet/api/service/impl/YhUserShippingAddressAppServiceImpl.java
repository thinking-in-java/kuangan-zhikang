package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserShippingAddressApp;
import com.yihe.applet.api.mapper.YhUserShippingAddressAppMapper;
import com.yihe.applet.api.service.YhUserShippingAddressAppService;
@Service
public class YhUserShippingAddressAppServiceImpl implements YhUserShippingAddressAppService{
	@Autowired
	YhUserShippingAddressAppMapper yhUserShippingAddressAppMapper;

	@Override
	public int deleteByPrimaryKey(Integer siteId) {
		// TODO Auto-generated method stub
		return yhUserShippingAddressAppMapper.deleteByPrimaryKey(siteId);
	}

	@Override
	public int insertSelective(YhUserShippingAddressApp record) {
		// TODO Auto-generated method stub
		return yhUserShippingAddressAppMapper.insertSelective(record);
	}

	@Override
	public YhUserShippingAddressApp selectByPrimaryKey(Integer siteId) {
		// TODO Auto-generated method stub
		return yhUserShippingAddressAppMapper.selectByPrimaryKey(siteId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserShippingAddressApp record) {
		// TODO Auto-generated method stub
		return yhUserShippingAddressAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserShippingAddressApp selectNewestShippingAddress(String userCode) {
		// TODO Auto-generated method stub
		return yhUserShippingAddressAppMapper.selectNewestShippingAddress(userCode);
	}

	@Override
	public List<YhUserShippingAddressApp> selectAllShippingAddress(String userCode) {
		// TODO Auto-generated method stub
		return yhUserShippingAddressAppMapper.selectAllShippingAddress(userCode);
	}

	@Override
	public int updateUserAddressStatus(String userCode) {
		// TODO Auto-generated method stub
		return yhUserShippingAddressAppMapper.updateUserAddressStatus(userCode);
	}
}
