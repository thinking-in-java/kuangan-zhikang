package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonageNonimmune;
import com.yihe.system.mapper.YhUserPersonageNonimmuneMapper;
import com.yihe.system.service.YhUserPersonageNonimmuneService;
@Service
public class YhUserPersonageNonimmuneServiceImpl implements YhUserPersonageNonimmuneService{

	@Autowired
	YhUserPersonageNonimmuneMapper  yhUserPersonageNonimmuneMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageNonimmuneMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(YhUserPersonageNonimmune yhUserPersonageNonimmune) {
		// TODO Auto-generated method stub
		return yhUserPersonageNonimmuneMapper.insert(yhUserPersonageNonimmune);
	}

	@Override
	public List<YhUserPersonageNonimmune> selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageNonimmuneMapper.selectByPrimaryKey(userCode);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonageNonimmune yhUserPersonageNonimmune) {
		// TODO Auto-generated method stub
		return yhUserPersonageNonimmuneMapper.updateByPrimaryKey(yhUserPersonageNonimmune);
	}

}
