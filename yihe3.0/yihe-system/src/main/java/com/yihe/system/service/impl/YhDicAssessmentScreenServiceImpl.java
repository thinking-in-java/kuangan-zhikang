package com.yihe.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHDocGroup;
import com.yihe.system.domain.YhDicAssessmentScreen;
import com.yihe.system.domain.YhDicAssessmentScreenExample;
import com.yihe.system.mapper.YhDicAssessmentScreenMapper;
import com.yihe.system.service.YhDicAssessmentScreenService;

/**
* YhDicAssessmentScreenService实现
* Created by dyb on 2020/3/23.
*/
@Service
public class YhDicAssessmentScreenServiceImpl implements YhDicAssessmentScreenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhDicAssessmentScreenServiceImpl.class);

    @Autowired
    YhDicAssessmentScreenMapper yhDicAssessmentScreenMapper;

	@Override
	public long countByExample(YhDicAssessmentScreenExample example) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(YhDicAssessmentScreenExample example) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer sid) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.deleteByPrimaryKey(sid);
	}

	@Override
	public int insert(YhDicAssessmentScreen record) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.insert(record);
	}

	@Override
	public int insertSelective(YhDicAssessmentScreen record) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.insertSelective(record);
	}

	@Override
	public PageInfo<YhDicAssessmentScreen> selectByExample(YhDicAssessmentScreenExample example,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhDicAssessmentScreen> list = yhDicAssessmentScreenMapper.selectByExample(example);
		return new PageInfo<YhDicAssessmentScreen>(list);
	}

	@Override
	public YhDicAssessmentScreen selectByPrimaryKey(Integer sid) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.selectByPrimaryKey(sid);
	}

	@Override
	public int updateByExampleSelective(YhDicAssessmentScreen record, YhDicAssessmentScreenExample example) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(YhDicAssessmentScreen record, YhDicAssessmentScreenExample example) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(YhDicAssessmentScreen record) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhDicAssessmentScreen record) {
		// TODO Auto-generated method stub
		return yhDicAssessmentScreenMapper.updateByPrimaryKey(record);
	}

}