package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonageHospitalized;
import com.yihe.system.mapper.YhUserPersonageHospitalizedMapper;
import com.yihe.system.service.YhUserPersonageHospitalizedService;
@Service
public class YhUserPersonageHospitalizedServiceImpl implements YhUserPersonageHospitalizedService{
	@Autowired
	YhUserPersonageHospitalizedMapper yhUserPersonageHospitalizedMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonageHospitalized yhUserPersonageHospitalized) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedMapper.updateByPrimaryKeySelective(yhUserPersonageHospitalized);
	}

	@Override
	public List<YhUserPersonageHospitalized> selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedMapper.selectByPrimaryKey(userCode);
	}

	@Override
	public int deleteAll(String  userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedMapper.deleteAll(userCode);
	}



	@Override
	public int insert(YhUserPersonageHospitalized yhUserPersonageHospitalized) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedMapper.insert(yhUserPersonageHospitalized);
	}

	@Override
	public YhUserPersonageHospitalized selectHospitalized(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageHospitalizedMapper.selectHospitalized(userCode);
	}

}
