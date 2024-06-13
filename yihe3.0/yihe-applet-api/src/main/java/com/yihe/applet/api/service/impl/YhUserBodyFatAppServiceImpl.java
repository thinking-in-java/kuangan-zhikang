package com.yihe.applet.api.service.impl;
 

import java.util.List;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserBodyFatApp;
import com.yihe.applet.api.mapper.YhUserBodyFatAppMapper;
import com.yihe.applet.api.service.YhUserBodyFatAppService;

/**
* YhUserBodyFatService实现
* Created by dyb on 2019/12/23.
*/
@Service
public class YhUserBodyFatAppServiceImpl implements YhUserBodyFatAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserBodyFatAppServiceImpl.class);

    @Autowired
    YhUserBodyFatAppMapper yhUserBodyFatMapper;

	@Override
	public List<YhUserBodyFatApp> selectByPrimaryKeyByEidLimit7(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.selectByPrimaryKeyByEidLimit7(usercode, timetype, strTime, endTime, page);
	}

	@Override
	public List<YhUserBodyFatApp> selectByPrimaryKeyBYeid(String eid) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.selectByPrimaryKeyBYeid(eid);
	}

	@Override
	public List<YhUserBodyFatApp> selectByPrimaryKeyByMonthInfo(String eid, String shijian) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.selectByPrimaryKeyByMonthInfo(eid, shijian);
	}

	@Override
	public YhUserBodyFatApp selectByPrimaryKeyNewest(String eid) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.selectByPrimaryKeyNewest(eid);
	}

	@Override
	public List<YhUserBodyFatApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public int deleteByPrimaryKey(Integer wubfId) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.deleteByPrimaryKey(wubfId);
	}

	@Override
	public int insertSelective(YhUserBodyFatApp record) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserBodyFatApp record) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserBodyFatApp selectByPrimaryKey(Integer wubfId) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.selectByPrimaryKey(wubfId);
	}

	@Override
	public List<YhUserBodyFatApp> findBodyFatAllByUserCode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserBodyFatMapper.findBodyFatAllByUserCode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserBodyFatApp> findBodyFatByYear(String time, String usercode, Integer pageNum, Integer pageSize) {
		return yhUserBodyFatMapper.findBodyFatByYear(time, usercode, pageNum, pageSize);
	}

	@Override
	public List<YhUserBodyFatApp> findBodyFatByMonth(String month, String usercode, Integer pageNum, Integer pageSize) {
		return yhUserBodyFatMapper.findBodyFatByMonth(month, usercode, pageNum, pageSize);
	}

	@Override
	public List<YhUserBodyFatApp> findBodyFatByWeek(String strTime, String endTime, String usercode, Integer pageNum, Integer pageSize) {
		return yhUserBodyFatMapper.findBodyFatByWeek(strTime, endTime, usercode, pageNum, pageSize);
	}

	@Override
	public List<Integer> findBodyFatTimeFenBuYear(String time, String usercode, Integer page) {
		return yhUserBodyFatMapper.findBodyFatTimeFenBuYear(time, usercode, page);
	}

	@Override
	public List<Integer> findBodyFatTimeFenBuMonth(String month, String usercode, Integer page) {
		return yhUserBodyFatMapper.findBodyFatTimeFenBuMonth(month, usercode, page);
	}

	@Override
	public List<Integer> findBodyFatTimeFenBuWeek(String strTime, String endTime, String usercode, Integer page) {
		return yhUserBodyFatMapper.findBodyFatTimeFenBuWeek(strTime, endTime, usercode, page);
	}

	@Override
	public List<String> selectByPrimaryKeyFindEntryTime(String usercode) {
		return yhUserBodyFatMapper.selectByPrimaryKeyFindEntryTime(usercode);
	}

	@Override
	public List<Integer> findtemperatureTimeFenBu(String usercode) {
		// TODO Auto-generated method stub
		return yhUserBodyFatMapper.findtemperatureTimeFenBu(usercode);
	}

}