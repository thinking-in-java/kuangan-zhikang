package com.yihe.applet.api.service.impl;

 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserBloodfatApp;
import com.yihe.applet.api.mapper.YhUserBloodfatAppMapper;
import com.yihe.applet.api.service.YhUserBloodfatAppService;

/**
* YhUserBloodfatService实现
* Created by dyb on 2019/12/23.
*/
@Service
public class YhUserBloodfatAppServiceImpl implements YhUserBloodfatAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserBloodfatAppServiceImpl.class);

    @Autowired
    YhUserBloodfatAppMapper yhUserBloodfatMapper;
	
	@Override
	public List<YhUserBloodfatApp> selectByPrimaryKeyBYeid(String eid) {
		// TODO Auto-generated method stub
		return yhUserBloodfatMapper.selectByPrimaryKeyBYeid(eid);
	}

	@Override
	public YhUserBloodfatApp selectByPrimaryKeyNewest(String eid) {
		// TODO Auto-generated method stub
		return yhUserBloodfatMapper.selectByPrimaryKeyNewest(eid);
	}

	@Override
	public List<YhUserBloodfatApp> selectByPrimaryKeyFindEntryTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserBloodfatMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public List<YhUserBloodfatApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserBloodfatMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public List<YhUserBloodfatApp> findBloodfatAllByUserCode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserBloodfatMapper.findBloodfatAllByUserCode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserBloodfatApp> findBingTuInfo(String usercode, Integer timetype, String strTime, String endTime, Integer page) {
		return yhUserBloodfatMapper.findBingTuInfo(usercode, timetype, strTime, endTime, page);
	}

	@Override
	public List<YhUserBloodfatApp> findTemperatureByYear(String time, String usercode, Integer page, Integer pagesize) {
		return yhUserBloodfatMapper.findTemperatureByYear(time, usercode, page, pagesize);
	}

	@Override
	public List<YhUserBloodfatApp> findTemperatureByMonth(String month, String usercode, Integer page, Integer pagesize) {
		return yhUserBloodfatMapper.findTemperatureByMonth(month, usercode, page, pagesize);
	}

	@Override
	public List<YhUserBloodfatApp> findTemperatureByWeek(String strTime, String endTime, String usercode, Integer page, Integer pagesize) {
		return yhUserBloodfatMapper.findTemperatureByWeek(strTime, endTime, usercode, page, pagesize);
	}

	@Override
	public int deleteByPrimaryKey(Integer uBloodfatId) {
		// TODO Auto-generated method stub
		return yhUserBloodfatMapper.deleteByPrimaryKey(uBloodfatId);
	}

	@Override
	public int insertSelective(YhUserBloodfatApp record) {
		// TODO Auto-generated method stub
		return yhUserBloodfatMapper.insertSelective(record);
	}

	@Override
	public YhUserBloodfatApp selectByPrimaryKey(Integer uBloodfatId) {
		// TODO Auto-generated method stub
		return yhUserBloodfatMapper.selectByPrimaryKey(uBloodfatId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserBloodfatApp record) {
		// TODO Auto-generated method stub
		return yhUserBloodfatMapper.updateByPrimaryKeySelective(record);
	}

}