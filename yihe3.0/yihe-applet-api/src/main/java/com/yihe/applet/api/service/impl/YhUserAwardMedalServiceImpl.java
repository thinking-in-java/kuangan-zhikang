package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserAwardMedal;
import com.yihe.applet.api.mapper.YhUserAwardMedalMapper;
import com.yihe.applet.api.service.YhUserAwardMedalService;

@Service
public class YhUserAwardMedalServiceImpl implements YhUserAwardMedalService{
	@Autowired
	YhUserAwardMedalMapper yhUserAwardMedalMapper;

	@Override
	public List<YhUserAwardMedal> selectYhUserAwardMedal(String userCode) {
		// TODO Auto-generated method stub
		return yhUserAwardMedalMapper.selectYhUserAwardMedal(userCode);
	}
}
