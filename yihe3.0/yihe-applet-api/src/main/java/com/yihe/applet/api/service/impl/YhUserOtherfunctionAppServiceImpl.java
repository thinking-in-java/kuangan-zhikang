package com.yihe.applet.api.service.impl;

 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.applet.api.domain.YhUserOtherfunctionApp;
import com.yihe.applet.api.mapper.YhUserOtherfunctionAppMapper;
import com.yihe.applet.api.service.YhUserOtherfunctionAppService;

/**
* YhUserOtherfunctionService实现
* Created by dyb on 2019/12/24.
*/
@Service
public class YhUserOtherfunctionAppServiceImpl  implements YhUserOtherfunctionAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserOtherfunctionAppServiceImpl.class);

    @Autowired
    YhUserOtherfunctionAppMapper yhUserOtherfunctionMapper;

	@Override
	public List<YhUserOtherfunctionApp> selectByPrimaryKeyBYeid(String eid) {
		// TODO Auto-generated method stub
		return yhUserOtherfunctionMapper.selectByPrimaryKeyBYeid(eid);
	}

	@Override
	public YhUserOtherfunctionApp selectByPrimaryKeyNewest(String eid) {
		// TODO Auto-generated method stub
		return yhUserOtherfunctionMapper.selectByPrimaryKeyNewest(eid);
	}

	@Override
	public List<YhUserOtherfunctionApp> selectByPrimaryKeyFindEntryTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserOtherfunctionMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public List<YhUserOtherfunctionApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserOtherfunctionMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public List<YhUserOtherfunctionApp> findOtherAllByUserCode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserOtherfunctionMapper.findOtherAllByUserCode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserOtherfunctionApp> findOherInfoByTimeType(String usercode, Integer timetype, String strTime, String endTime) {
		return yhUserOtherfunctionMapper.findOherInfoByTimeType(usercode, timetype, strTime, endTime);
	}

	@Override
	public int deleteByPrimaryKey(Integer otherid) {
		// TODO Auto-generated method stub
		return yhUserOtherfunctionMapper.deleteByPrimaryKey(otherid);
	}

	@Override
	public int insertSelective(YhUserOtherfunctionApp record) {
		// TODO Auto-generated method stub
		return yhUserOtherfunctionMapper.insertSelective(record);
	}

	@Override
	public YhUserOtherfunctionApp selectByPrimaryKey(Integer otherid) {
		// TODO Auto-generated method stub
		return yhUserOtherfunctionMapper.selectByPrimaryKey(otherid);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserOtherfunctionApp record) {
		// TODO Auto-generated method stub
		return yhUserOtherfunctionMapper.updateByPrimaryKeySelective(record);
	}

}