package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhHealthLectureInfo;
import com.yihe.system.mapper.YhHealthLectureInfoMapper;
import com.yihe.system.service.YhHealthLectureInfoService;

/**
* YhHealthLectureInfoService实现
* Created by dong on 2020/3/27.
*/
@Service
public class YhHealthLectureInfoServiceImpl  implements YhHealthLectureInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthLectureInfoServiceImpl.class);

    @Autowired
    YhHealthLectureInfoMapper yhHealthLectureInfoMapper;

    @Override
	public int selectBylectureCount(Integer categoryId) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoMapper.selectBylectureCount(categoryId);
	}

	@Override
	public List<YhHealthLectureInfo> selectLectureInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoMapper.selectLectureInfo(map);
	}

	@Override
	public int selectLectureInfoConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoMapper.selectLectureInfoConut(map);
	}

	@Override
	public int insertSelective(YhHealthLectureInfo record) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoMapper.insertSelective(record);
	}

	@Override
	public YhHealthLectureInfo selectByPrimaryKey(Integer infoid) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoMapper.selectByPrimaryKey(infoid);
	}

	@Override
	public int updateByPrimaryKeySelective(YhHealthLectureInfo record) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer infoid) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoMapper.deleteByPrimaryKey(infoid);
	}

	@Override
	public List<YhHealthLectureInfo> selectByExample() {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoMapper.selectByExample();
	}

	@Override
	public int selctTitleRepetition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthLectureInfoMapper.selctTitleRepetition(map);
	}

}