package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonageNonimmuneApp;
import com.yihe.applet.api.mapper.YhUserPersonageNonimmuneAppMapper;
import com.yihe.applet.api.service.YhUserPersonageNonimmuneAppService;
@Service
public class YhUserPersonageNonimmuneAppServiceImpl implements YhUserPersonageNonimmuneAppService{
		@Autowired
		YhUserPersonageNonimmuneAppMapper  yhUserPersonageNonimmuneAppMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageNonimmuneAppMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(YhUserPersonageNonimmuneApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageNonimmuneAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonageNonimmuneApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageNonimmuneAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageNonimmuneApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageNonimmuneAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<YhUserPersonageNonimmuneApp> shownonimmune(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageNonimmuneAppMapper.shownonimmune(userCode);
	}

}
