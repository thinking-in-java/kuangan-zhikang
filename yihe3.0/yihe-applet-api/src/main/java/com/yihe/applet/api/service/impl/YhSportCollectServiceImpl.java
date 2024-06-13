package com.yihe.applet.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhSportCollect;
import com.yihe.applet.api.mapper.YhSportCollectMapper;
import com.yihe.applet.api.service.YhSportCollectService;

/**
* YhSportCollectService实现
* Created by dong on 2020/2/25.
*/
@Service
public class YhSportCollectServiceImpl implements YhSportCollectService{

	@Resource
    YhSportCollectMapper yhSportCollectMapper;

	@Override
	public int insertSelective(YhSportCollect record) {
		// TODO Auto-generated method stub
		return yhSportCollectMapper.insertSelective(record);
	}

	@Override
	public int showCollectRepetition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportCollectMapper.showCollectRepetition(map);
	}

	@Override
	public int deleteByPrimaryKeymap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportCollectMapper.deleteByPrimaryKeymap(map);
	}

}