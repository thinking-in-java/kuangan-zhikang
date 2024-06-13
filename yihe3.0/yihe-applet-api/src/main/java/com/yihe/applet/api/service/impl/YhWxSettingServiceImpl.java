package com.yihe.applet.api.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.applet.api.domain.YhWxSetting;
import com.yihe.applet.api.mapper.YhWxSettingMapper;
import com.yihe.applet.api.service.YhWxSettingService;

/**
* YhWxSettingService实现
* Created by dong on 2020/2/26.
*/
@Service
public class YhWxSettingServiceImpl implements YhWxSettingService {

	@Resource
    YhWxSettingMapper yhWxSettingMapper;

	@Override
	public YhWxSetting selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhWxSettingMapper.selectByPrimaryKey(id);
	}

}