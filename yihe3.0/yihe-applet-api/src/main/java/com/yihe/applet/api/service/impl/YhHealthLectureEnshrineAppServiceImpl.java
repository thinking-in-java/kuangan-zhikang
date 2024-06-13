package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhHealthLectureEnshrineApp;
import com.yihe.applet.api.mapper.YhHealthLectureEnshrineAppMapper;
import com.yihe.applet.api.service.YhHealthLectureEnshrineAppService;

@Service
public class YhHealthLectureEnshrineAppServiceImpl implements YhHealthLectureEnshrineAppService{
	@Autowired
	YhHealthLectureEnshrineAppMapper yhHealthLectureEnshrineAppMapper;

	@Override
	public YhHealthLectureEnshrineApp selectWhetherEnshrineByParam(String userCode, Integer categoryid) {
		// TODO Auto-generated method stub
		return yhHealthLectureEnshrineAppMapper.selectWhetherEnshrineByParam(userCode, categoryid);
	}

	@Override
	public int deleteWhetherEnshrineByParam(String userCode, Integer categoryid) {
		// TODO Auto-generated method stub
		return yhHealthLectureEnshrineAppMapper.deleteWhetherEnshrineByParam(userCode, categoryid);
	}

	@Override
	public int insertSelective(YhHealthLectureEnshrineApp record) {
		// TODO Auto-generated method stub
		return yhHealthLectureEnshrineAppMapper.insertSelective(record);
	}
	
	
}
