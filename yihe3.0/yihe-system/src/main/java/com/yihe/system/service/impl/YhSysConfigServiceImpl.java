package com.yihe.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhSysConfig;
import com.yihe.system.mapper.YhSysConfigMapper;
import com.yihe.system.service.YhSysConfigService;

/**
* YhSysConfigService实现
* Created by royiwen on 2020/3/25.
*/
@Service
@Transactional

public class YhSysConfigServiceImpl  implements YhSysConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhSysConfigServiceImpl.class);

    @Autowired
    YhSysConfigMapper yhSysConfigMapper;

	@Override
	public YhSysConfig showConfig() {
		// TODO Auto-generated method stub
		return yhSysConfigMapper.showConfig();
	}

}