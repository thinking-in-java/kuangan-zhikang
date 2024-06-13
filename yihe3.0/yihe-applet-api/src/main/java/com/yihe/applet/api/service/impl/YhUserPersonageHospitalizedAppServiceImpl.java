package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonageHospitalizedApp;
import com.yihe.applet.api.mapper.YhUserPersonageHospitalizedAppMapper;
import com.yihe.applet.api.service.YhUserPersonageHospitalizedAppService;
@Service
public class YhUserPersonageHospitalizedAppServiceImpl implements YhUserPersonageHospitalizedAppService{
	@Autowired
	YhUserPersonageHospitalizedAppMapper yhUserPersonageHospitalizedAppMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedAppMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(YhUserPersonageHospitalizedApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonageHospitalizedApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageHospitalizedApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<YhUserPersonageHospitalizedApp> showhospitalized(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedAppMapper.showhospitalized(userCode);
	}

}
