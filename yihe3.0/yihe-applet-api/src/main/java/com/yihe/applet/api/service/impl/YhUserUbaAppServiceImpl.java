package com.yihe.applet.api.service.impl;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserUbaApp;
import com.yihe.applet.api.mapper.YhUserUbaAppMapper;
import com.yihe.applet.api.service.YhUserUbaAppService;

/**
* YhUserUbaService实现
* Created by dyb on 2019/12/25.
*/
@Service
public class YhUserUbaAppServiceImpl   implements YhUserUbaAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserUbaAppServiceImpl.class);

    @Autowired
    YhUserUbaAppMapper yhUserUbaMapper;

	@Override
	public List<YhUserUbaApp> selectByPrimaryKeyBYeid(String eid) {
		// TODO Auto-generated method stub
		return yhUserUbaMapper.selectByPrimaryKeyBYeid(eid);
	}

	@Override
	public YhUserUbaApp selectByPrimaryKeyNewest(String eid) {
		// TODO Auto-generated method stub
		return yhUserUbaMapper.selectByPrimaryKeyNewest(eid);
	}

	@Override
	public List<YhUserUbaApp> selectByPrimaryKeyFindEntryTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserUbaMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public List<YhUserUbaApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserUbaMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public List<YhUserUbaApp> findUserUbaAllByUserCode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserUbaMapper.findUserUbaAllByUserCode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserUbaApp> findUbaInfoByTimeType(String usercode, Integer timetype, String strTime, String endTime) {
		return yhUserUbaMapper.findUbaInfoByTimeType(usercode, timetype, strTime, endTime);
	}

	@Override
	public int deleteByPrimaryKey(Integer ubaId) {
		// TODO Auto-generated method stub
		return yhUserUbaMapper.deleteByPrimaryKey(ubaId);
	}

	@Override
	public int insertSelective(YhUserUbaApp record) {
		// TODO Auto-generated method stub
		return yhUserUbaMapper.insertSelective(record);
	}

	@Override
	public YhUserUbaApp selectByPrimaryKey(Integer ubaId) {
		// TODO Auto-generated method stub
		return yhUserUbaMapper.selectByPrimaryKey(ubaId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserUbaApp record) {
		// TODO Auto-generated method stub
		return yhUserUbaMapper.updateByPrimaryKeySelective(record);
	}

}