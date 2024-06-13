package com.yihe.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhDiseaseCategory;
import com.yihe.system.domain.YhDiseaseCategoryExample;
import com.yihe.system.mapper.YhDiseaseCategoryMapper;
import com.yihe.system.service.YhDiseaseCategoryService;

/**
* YhDiseaseCategoryService实现
* Created by dyb on 2020/3/19.
*/
@Service
public class YhDiseaseCategoryServiceImpl implements YhDiseaseCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhDiseaseCategoryServiceImpl.class);

    @Autowired
    YhDiseaseCategoryMapper yhDiseaseCategoryMapper;

	@Override
	public long countByExample(YhDiseaseCategoryExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(YhDiseaseCategoryExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(YhDiseaseCategory record) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.insert(record);
	}

	@Override
	public int insertSelective(YhDiseaseCategory record) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.insertSelective(record);
	}

	@Override
	public List<YhDiseaseCategory> selectByExample(YhDiseaseCategoryExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.selectByExample(example);
	}

	@Override
	public YhDiseaseCategory selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(YhDiseaseCategory record, YhDiseaseCategoryExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(YhDiseaseCategory record, YhDiseaseCategoryExample example) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(YhDiseaseCategory record) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhDiseaseCategory record) {
		// TODO Auto-generated method stub
		return yhDiseaseCategoryMapper.updateByPrimaryKey(record);
	}

}