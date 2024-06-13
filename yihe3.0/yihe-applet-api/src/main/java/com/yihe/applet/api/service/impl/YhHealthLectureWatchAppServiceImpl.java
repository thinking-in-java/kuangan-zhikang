package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhHealthLectureWatchApp;
import com.yihe.applet.api.mapper.YhHealthLectureWatchAppMapper;
import com.yihe.applet.api.service.YhHealthLectureWatchAppService;
@Service
public class YhHealthLectureWatchAppServiceImpl implements YhHealthLectureWatchAppService{
	@Autowired
	YhHealthLectureWatchAppMapper yhHealthLectureWatchAppMapper;

	@Override
	public int insertSelective(YhHealthLectureWatchApp record) {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchAppMapper.insertSelective(record);
	}

	@Override
	public YhHealthLectureWatchApp selectByPrimaryKey(Integer watchid) {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchAppMapper.selectByPrimaryKey(watchid);
	}

	@Override
	public int updateByPrimaryKeySelective(YhHealthLectureWatchApp record) {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhHealthLectureWatchApp record) {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchAppMapper.updateByPrimaryKey(record);
	}

	@Override
	public YhHealthLectureWatchApp selectYhHealthLectureWatch(String classId) {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchAppMapper.selectYhHealthLectureWatch(classId);
	}

	@Override
	public int update(YhHealthLectureWatchApp record) {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchAppMapper.update(record);
	}

	@Override
	public YhHealthLectureWatchApp selectclassIdByParam(String userCode, Integer classId) {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchAppMapper.selectclassIdByParam(userCode, classId);
	}
}
