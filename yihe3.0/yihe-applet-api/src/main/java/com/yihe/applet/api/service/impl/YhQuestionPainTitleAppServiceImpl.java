package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhQuestionPainTitleApp;
import com.yihe.applet.api.mapper.YhQuestionPainTitleAppMapper;
import com.yihe.applet.api.service.YhQuestionPainTitleAppService;
@Service
public class YhQuestionPainTitleAppServiceImpl implements YhQuestionPainTitleAppService{
	@Autowired
	YhQuestionPainTitleAppMapper yhQuestionPainTitleAppMapper;

	@Override
	public List<YhQuestionPainTitleApp> selectAll() {
		// TODO Auto-generated method stub
		return yhQuestionPainTitleAppMapper.selectAll();
	}
}
