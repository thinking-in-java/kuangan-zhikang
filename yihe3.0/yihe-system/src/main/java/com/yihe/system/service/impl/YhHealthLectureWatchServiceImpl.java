package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.mapper.YhHealthLectureWatchMapper;
import com.yihe.system.service.YhHealthLectureWatchService;

@Service
public class YhHealthLectureWatchServiceImpl implements YhHealthLectureWatchService{
	@Autowired
	YhHealthLectureWatchMapper yhHealthLectureWatchMapper;

	@Override
	public List<Map<String, Object>> selectexportClassroomData(Integer infoid, Integer categoryId) {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchMapper.selectexportClassroomData(infoid, categoryId);
	}

	@Override
	public List<Map<String, Object>> selecteAllClassroomData() {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchMapper.selecteAllClassroomData();
	}

	@Override
	public List<Map<String, Object>> selecteYesterdayAllClassroomData() {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchMapper.selecteYesterdayAllClassroomData();
	}

	@Override
	public List<Map<String, Object>> selecteThisweekAllClassroomData() {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchMapper.selecteThisweekAllClassroomData();
	}

	@Override
	public List<Map<String, Object>> selectelaskweekAllClassroomData() {
		// TODO Auto-generated method stub
		return yhHealthLectureWatchMapper.selectelaskweekAllClassroomData();
	}
}
