package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonagePharmacyApp;
import com.yihe.applet.api.mapper.YhUserPersonagePharmacyAppMapper;
import com.yihe.applet.api.service.YhUserPersonagePharmacyAppService;

@Service
public class YhUserPersonagePharmacyAppServiceImpl implements YhUserPersonagePharmacyAppService{
	@Autowired
	YhUserPersonagePharmacyAppMapper yhUserPersonagePharmacyAppMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonagePharmacyAppMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(YhUserPersonagePharmacyApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonagePharmacyAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonagePharmacyApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonagePharmacyAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonagePharmacyApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonagePharmacyAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<YhUserPersonagePharmacyApp> showpharmacy(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonagePharmacyAppMapper.showpharmacy(userCode);
	}

}
