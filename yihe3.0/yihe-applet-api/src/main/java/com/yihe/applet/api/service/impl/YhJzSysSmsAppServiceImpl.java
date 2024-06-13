package com.yihe.applet.api.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.applet.api.domain.YhJzSysSmsApp;
import com.yihe.applet.api.mapper.YhjzsysSmsAppMapper;
import com.yihe.applet.api.service.YhJzSysSmsAppService;

/**
* YhJzSysSmsService实现
* Created by dong on 2020/2/26.
*/
@Service
public class YhJzSysSmsAppServiceImpl  implements YhJzSysSmsAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhJzSysSmsAppServiceImpl.class);

    @Autowired
    YhjzsysSmsAppMapper yhjzsysSmsAppMapper;

	@Override
	public YhJzSysSmsApp selectById(Integer smsId) {
		// TODO Auto-generated method stub
		return yhjzsysSmsAppMapper.selectById(smsId);
	}

	@Override
	public List<YhJzSysSmsApp> selectYhJzSysSmsAppBytype() {
		// TODO Auto-generated method stub
		return yhjzsysSmsAppMapper.selectYhJzSysSmsAppBytype();
	}

}