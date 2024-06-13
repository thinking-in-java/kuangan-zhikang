package com.yihe.applet.api.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserBloodsugerApp;
import com.yihe.applet.api.mapper.YhUserBloodsugerAppMapper;
import com.yihe.applet.api.service.YhUserBloodsugerAppService;
 

/**
 * YhUserBloodsugerService实现 Created by dyb on 2019/12/20.
 */
@Service
public class YhUserBloodsugerAppServiceImpl
		implements YhUserBloodsugerAppService {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserBloodsugerAppServiceImpl.class);

	@Autowired
	YhUserBloodsugerAppMapper yhUserBloodsugerMapper;

	@Override
	public List<YhUserBloodsugerApp> selectByPrimaryKeyByEidLimit7(String eid) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.selectByPrimaryKeyByEidLimit7(eid);
	}

	@Override
	public List<YhUserBloodsugerApp> selectByPrimaryKeyBYeid(String usercode, String type) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.selectByPrimaryKeyBYeid(usercode,type);
	}

	@Override
	public List<YhUserBloodsugerApp> selectByPrimaryKeyBYeid2(String usercode, Integer timetype, String strTime, String endTime, Integer page, String type) {
		return yhUserBloodsugerMapper.selectByPrimaryKeyBYeid2(usercode,timetype,strTime,endTime,page,type);
	}

	@Override
	public List<YhUserBloodsugerApp> selectByPrimaryKeyByMonthInfo(String eid, String shijian) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.selectByPrimaryKeyByMonthInfo(eid, shijian);
	}

	@Override
	public YhUserBloodsugerApp selectByPrimaryKeyNewest(String eid) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.selectByPrimaryKeyNewest(eid);
	}

	@Override
	public List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public List<YhUserBloodsugerApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public YhUserBloodsugerApp selectByPrimaryKey(Integer uBloodsugerId) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.selectByPrimaryKey(uBloodsugerId);
	}

	@Override
	public int insertSelective(YhUserBloodsugerApp record) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserBloodsugerApp record) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<YhUserBloodsugerApp> findBloodsugerAllByUserCode(String usercode,Integer timetype,String strTime,String endTime,String type) {
		return yhUserBloodsugerMapper.findBloodsugerAllByUserCode(usercode, timetype, strTime, endTime, type);
	}

	@Override
	public List<YhUserBloodsugerApp> findBloodsugerByYear(String type,String time, String usercode, Integer page, Integer pagesize) {
		return yhUserBloodsugerMapper.findBloodsugerByYear(type,time,usercode,page,pagesize);
	}

	@Override
	public List<YhUserBloodsugerApp> findBloodsugerByMonth(String type,String month, String usercode, Integer page, Integer pagesize) {
		return yhUserBloodsugerMapper.findBloodsugerByMonth(type,month,usercode,page,pagesize);
	}

	@Override
	public List<YhUserBloodsugerApp> findBloodsugerByWeek(String type,String strTime, String endTime, String usercode, Integer page, Integer pagesize) {
		return yhUserBloodsugerMapper.findBloodsugerByWeek(type,strTime,endTime,usercode,page,pagesize);
	}

	@Override
	public List<Integer> findbloodsugerTimeFenBuYear(String time, String usercode, Integer page, String type) {
		return yhUserBloodsugerMapper.findbloodsugerTimeFenBuYear(time,usercode,page,type);
	}

	@Override
	public List<Integer> findbloodsugerTimeFenBuMonth(String month, String usercode, Integer page, String type) {
		return yhUserBloodsugerMapper.findbloodsugerTimeFenBuMonth(month,usercode,page,type);
	}

	@Override
	public List<Integer> findbloodsugerTimeFenBuWeek(String strTime, String endTime, String usercode, Integer page, String type) {
		return yhUserBloodsugerMapper.findbloodsugerTimeFenBuWeek(strTime,endTime,usercode,page,type);
	}

	@Override
	public List<Integer> findtemperatureTimeFenBu(String usercode) {
		// TODO Auto-generated method stub
		return yhUserBloodsugerMapper.findtemperatureTimeFenBu(usercode);
	}

}