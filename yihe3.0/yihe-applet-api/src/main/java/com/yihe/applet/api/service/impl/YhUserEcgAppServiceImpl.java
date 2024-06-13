package com.yihe.applet.api.service.impl;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserEcgApp;
import com.yihe.applet.api.mapper.YhUserEcgAppMapper;
import com.yihe.applet.api.service.YhUserEcgAppService;

/**
* YhUserEcgService实现
* Created by dyb on 2019/12/25.
*/
@Service
public class YhUserEcgAppServiceImpl   implements YhUserEcgAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserEcgAppServiceImpl.class);

    @Autowired
    YhUserEcgAppMapper yhUserEcgMapper;

	@Override
	public YhUserEcgApp selectByPrimaryKeyOrderByTimeLimit1(String uEcgId) {
		// TODO Auto-generated method stub
		return yhUserEcgMapper.selectByPrimaryKeyOrderByTimeLimit1(uEcgId);
	}

	@Override
	public List<YhUserEcgApp> selectByPrimaryKeyFindEntryTime(Integer E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserEcgMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public List<YhUserEcgApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, Integer usercode) {
		// TODO Auto-generated method stub
		return yhUserEcgMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public int deleteByPrimaryKey(Integer uEcgId) {
		// TODO Auto-generated method stub
		return yhUserEcgMapper.deleteByPrimaryKey(uEcgId);
	}

	@Override
	public int insertSelective(YhUserEcgApp record) {
		// TODO Auto-generated method stub
		return yhUserEcgMapper.insertSelective(record);
	}

	@Override
	public YhUserEcgApp selectByPrimaryKey(Integer uEcgId) {
		// TODO Auto-generated method stub
		return yhUserEcgMapper.selectByPrimaryKey(uEcgId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserEcgApp record) {
		// TODO Auto-generated method stub
		return yhUserEcgMapper.updateByPrimaryKeySelective(record);
	}

}