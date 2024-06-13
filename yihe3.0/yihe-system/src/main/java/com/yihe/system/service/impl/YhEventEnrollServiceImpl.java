package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhEventEnroll;
import com.yihe.system.domain.YhEventEnrollExample;
import com.yihe.system.mapper.YhEventEnrollMapper;
import com.yihe.system.service.YhEventEnrollService;

/**
* YhEventEnrollService实现
* Created by dong on 2020/3/24.
*/
@Service
@Transactional

public class YhEventEnrollServiceImpl implements YhEventEnrollService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhEventEnrollServiceImpl.class);

    @Autowired
    YhEventEnrollMapper yhEventEnrollMapper;

	@Override
	public List<YhEventEnroll> selectEnrollAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhEventEnrollMapper.selectEnrollAll(map);
	}

	@Override
	public int selectEnrollAllConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhEventEnrollMapper.selectEnrollAllConut(map);
	}

	@Override
	public int insertSelective(YhEventEnroll record) {
		// TODO Auto-generated method stub
		return yhEventEnrollMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhEventEnrollMapper.deleteByPrimaryKey(id);
	}

	@Override
	public YhEventEnroll selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhEventEnrollMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKeySelective(YhEventEnroll record) {
		// TODO Auto-generated method stub
		return yhEventEnrollMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<YhEventEnroll> selectByExample() {
		// TODO Auto-generated method stub
		return yhEventEnrollMapper.selectByExample();
	}

	@Override
	public int selctEventNmaeRepetition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhEventEnrollMapper.selctEventNmaeRepetition(map);
	}

}