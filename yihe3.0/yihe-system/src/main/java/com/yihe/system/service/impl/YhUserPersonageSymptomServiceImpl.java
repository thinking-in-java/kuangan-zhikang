package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserPersonageSymptom;
import com.yihe.system.mapper.YhUserPersonageSymptomMapper;
import com.yihe.system.service.YhUserPersonageSymptomService;

/**
 * 症状实现类
 * @author yx
 * 2020年4月14日16:08:00
 *
 */
@Service
@Transactional
public class YhUserPersonageSymptomServiceImpl implements YhUserPersonageSymptomService{
	@Autowired
	YhUserPersonageSymptomMapper yhUserPersonageSymptomMapper;
	@Override
	public YhUserPersonageSymptom selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageSymptomMapper.selectByPrimaryKey(userCode);
	}
	@Override
	public int updateByPrimaryKey(YhUserPersonageSymptom yhUserPersonageSymptom) {
		// TODO Auto-generated method stub
		return yhUserPersonageSymptomMapper.updateByPrimaryKey(yhUserPersonageSymptom);
	}
	@Override
	public int insertSelective(YhUserPersonageSymptom yhUserPersonageSymptom) {
		// TODO Auto-generated method stub
		return yhUserPersonageSymptomMapper.insertSelective(yhUserPersonageSymptom);
	}
	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageSymptom yhUserPersonageSymptom) {
		// TODO Auto-generated method stub
		return yhUserPersonageSymptomMapper.updateByPrimaryKeySelective(yhUserPersonageSymptom);
	}

}
