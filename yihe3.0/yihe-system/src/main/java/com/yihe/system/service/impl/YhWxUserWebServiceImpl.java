package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhWxUserWeb;
import com.yihe.system.mapper.YhWxUserWebMapper;
import com.yihe.system.service.YhWxUserWebService;
@Service
public class YhWxUserWebServiceImpl implements YhWxUserWebService {
@Autowired
YhWxUserWebMapper YhWxUserWebMapper;
  
	@Override
	public YhWxUserWeb selectByPrimaryKey(String userId) {
		// TODO Auto-generated method stub
		return YhWxUserWebMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<String> findUseroppenIdByDoctorAll(String usercode,String groupid) {
		// TODO Auto-generated method stub
		return YhWxUserWebMapper.findUseroppenIdByDoctorAll(usercode,groupid);
	}

}
