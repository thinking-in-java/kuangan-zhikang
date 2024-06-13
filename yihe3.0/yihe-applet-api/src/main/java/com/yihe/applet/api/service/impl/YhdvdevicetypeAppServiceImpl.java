package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhdvdevicetypeApp;
import com.yihe.applet.api.mapper.YhdvdevicetypeAppMapper;
import com.yihe.applet.api.service.YhdvdevicetypeAppService;
@Service
public class YhdvdevicetypeAppServiceImpl implements YhdvdevicetypeAppService {

	@Autowired
	private YhdvdevicetypeAppMapper YhdvdevicetypeAppMapper;
	@Override
	public List<YhdvdevicetypeApp> findDeviceTypeListInfo() {
		// TODO Auto-generated method stub
		return YhdvdevicetypeAppMapper.findDeviceTypeListInfo();
	}

}
