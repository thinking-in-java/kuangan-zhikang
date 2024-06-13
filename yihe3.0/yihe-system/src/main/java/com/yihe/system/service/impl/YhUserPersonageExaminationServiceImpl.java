package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserPersonageExamination;
import com.yihe.system.mapper.YhUserPersonageExaminationMapper;
import com.yihe.system.service.YhUserPersonageExaminationService;
@Service
public class YhUserPersonageExaminationServiceImpl implements YhUserPersonageExaminationService{
	@Autowired
	YhUserPersonageExaminationMapper yhUserPersonageExaminationMapper;
	@Override
	public YhUserPersonageExamination selectByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhUserPersonageExaminationMapper.selectByPrimaryKey(userCode);
	}

	@Override
	public int updateByPrimaryKey(YhUserPersonageExamination yhUserPersonageExamination) {
		// TODO Auto-generated method stub
		return yhUserPersonageExaminationMapper.updateByPrimaryKey(yhUserPersonageExamination);
	}

	@Override
	public int insertSelective(YhUserPersonageExamination yhUserPersonageExamination) {
		// TODO Auto-generated method stub
		return yhUserPersonageExaminationMapper.insertSelective(yhUserPersonageExamination);
	}

}
