package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserOrderApp;
import com.yihe.applet.api.mapper.YhUserOrderAppMapper;
import com.yihe.applet.api.service.YhUserOrderAppService;
@Service
public class YhUserOrderAppServiceImpl implements YhUserOrderAppService{
	@Autowired
	YhUserOrderAppMapper yhUserOrderAppMapper;

	@Override
	public int insertSelective(YhUserOrderApp record) {
		// TODO Auto-generated method stub
		return yhUserOrderAppMapper.insertSelective(record);
	}

	@Override
	public List<YhUserOrderApp> selectUserAllOrderbyParam(Integer page, Integer pageSize, Integer month, Integer type,
			String userCode) {
		// TODO Auto-generated method stub
		List<YhUserOrderApp> list = yhUserOrderAppMapper.selectUserAllOrderbyParam(page, pageSize, month, type, userCode);
		return list;
	}
}
