package com.yihe.applet.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserRenalfunctionApp;
import com.yihe.applet.api.mapper.YhUserRenalfunctionAppMapper;
import com.yihe.applet.api.service.YhUserRenalfunctionAppService;

/**
* YhUserRenalfunctionService实现
* Created by dyb on 2019/12/24.
*/
@Service
public class YhUserRenalfunctionAppServiceImpl  implements YhUserRenalfunctionAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserRenalfunctionAppServiceImpl.class);

    @Autowired
    YhUserRenalfunctionAppMapper yhUserRenalfunctionMapper;

	@Override
	public List<YhUserRenalfunctionApp> selectByPrimaryKeyBYeid(String eid) {
		// TODO Auto-generated method stub
		return yhUserRenalfunctionMapper.selectByPrimaryKeyBYeid(eid);
	}

	@Override
	public YhUserRenalfunctionApp selectByPrimaryKeyNewest(String eid) {
		// TODO Auto-generated method stub
		return yhUserRenalfunctionMapper.selectByPrimaryKeyNewest(eid);
	}

	@Override
	public List<YhUserRenalfunctionApp> selectByPrimaryKeyFindEntryTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserRenalfunctionMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public List<YhUserRenalfunctionApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserRenalfunctionMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public List<YhUserRenalfunctionApp> findRenInfoAllByUserCode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserRenalfunctionMapper.findRenInfoAllByUserCode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserRenalfunctionApp> findRenInfoByTimeType(String usercode, Integer timetype, String strTime, String endTime) {
		return yhUserRenalfunctionMapper.findRenInfoByTimeType(usercode, timetype, strTime, endTime);
	}

	@Override
	public int deleteByPrimaryKey(Integer renalid) {
		// TODO Auto-generated method stub
		return yhUserRenalfunctionMapper.deleteByPrimaryKey(renalid);
	}

	@Override
	public int insertSelective(YhUserRenalfunctionApp record) {
		// TODO Auto-generated method stub
		return yhUserRenalfunctionMapper.insertSelective(record);
	}

	@Override
	public YhUserRenalfunctionApp selectByPrimaryKey(Integer renalid) {
		// TODO Auto-generated method stub
		return yhUserRenalfunctionMapper.selectByPrimaryKey(renalid);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserRenalfunctionApp record) {
		// TODO Auto-generated method stub
		return yhUserRenalfunctionMapper.updateByPrimaryKeySelective(record);
	}

}