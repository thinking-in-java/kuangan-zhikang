package com.yihe.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserLog;
import com.yihe.system.domain.YhUserLogExample;
import com.yihe.system.mapper.YhUserLogMapper;
import com.yihe.system.service.YhUserLogService;

/**
* YhUserLogService实现
* Created by lll on 2020/3/6.
*/
@Service
@Transactional

public class YhUserLogServiceImpl implements YhUserLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserLogServiceImpl.class);

    @Autowired
    YhUserLogMapper yhUserLogMapper;

	@Override
	public int insert(YhUserLog record) {
		// TODO Auto-generated method stub
		return yhUserLogMapper.insert(record);
	}

	@Override
	public List<YhUserLog> selectByExampleForOffsetPage(YhUserLogExample upmsLogExample, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByExample(YhUserLogExample upmsLogExample) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKeys(String ids) {
		// TODO Auto-generated method stub
		return 0;
	}

}