package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserDeviceApp;
import com.yihe.applet.api.mapper.YhUserDeviceAppMapper;
import com.yihe.applet.api.service.YhUserDeviceAppService;
@Service
public class YhUserDeviceAppServiceImpl implements YhUserDeviceAppService {
	@Autowired
	private YhUserDeviceAppMapper YhUserDeviceMapper;

	@Override
	public List<YhUserDeviceApp> findDeviceAllByUsercode(String usercode) {
		// TODO Auto-generated method stub
		return YhUserDeviceMapper.findDeviceAllByUsercode(usercode);
	}

	@Override
	public int deleteByPrimaryKey(Integer tid) {
		// TODO Auto-generated method stub
		return YhUserDeviceMapper.deleteByPrimaryKey(tid);
	}

	@Override
	public int insertSelective(YhUserDeviceApp record) {
		// TODO Auto-generated method stub
		return YhUserDeviceMapper.insertSelective(record);
	}

	@Override
	public YhUserDeviceApp selectByPrimaryKey(Integer tid) {
		// TODO Auto-generated method stub
		return YhUserDeviceMapper.selectByPrimaryKey(tid);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserDeviceApp record) {
		// TODO Auto-generated method stub
		return YhUserDeviceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int findExistenceDevice(String tid) {
		// TODO Auto-generated method stub
		return YhUserDeviceMapper.findExistenceDevice(tid);
	}

}
