package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserPersonageExaminationApp;
import com.yihe.applet.api.mapper.YhUserPersonageExaminationAppMapper;
import com.yihe.applet.api.service.YhUserPersonageExaminationAppService;

@Service
public class YhUserPersonageExaminationAppServiceImpl implements YhUserPersonageExaminationAppService{
	@Autowired
	YhUserPersonageExaminationAppMapper  yhUserPersonageExaminationAppMapper;

	@Override
	public int insertSelective(YhUserPersonageExaminationApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageExaminationAppMapper.insertSelective(record);
	}

	@Override
	public YhUserPersonageExaminationApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhUserPersonageExaminationAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserPersonageExaminationApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageExaminationAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonageExaminationApp record) {
		// TODO Auto-generated method stub
		return yhUserPersonageExaminationAppMapper.updateByPrimaryKey(record);
	}

	@Override
	public YhUserPersonageExaminationApp showexamination(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageExaminationAppMapper.showexamination(userCode);
	}
}
