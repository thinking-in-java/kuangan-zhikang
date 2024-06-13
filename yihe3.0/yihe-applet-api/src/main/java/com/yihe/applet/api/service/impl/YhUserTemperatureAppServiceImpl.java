package com.yihe.applet.api.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserTemperatureApp;
import com.yihe.applet.api.mapper.YhUserTemperatureAppMapper;
import com.yihe.applet.api.service.YhUserTemperatureAppService;

/**
 * YhUserTemperatureService实现 Created by dyb on 2020/3/3.
 */
@Service
public class YhUserTemperatureAppServiceImpl
		implements YhUserTemperatureAppService {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserTemperatureAppServiceImpl.class);

	@Autowired
	YhUserTemperatureAppMapper yhUserTemperatureMapper;

	@Override
	public YhUserTemperatureApp selectByPrimaryKeyOrderTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.selectByPrimaryKeyOrderTime(E_User_Id);
	}

	@Override
	public List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public List<YhUserTemperatureApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public List<YhUserTemperatureApp> findUserTemPerAtureByUsercode(String usercode) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.findUserTemPerAtureByUsercode(usercode);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserTemperatureApp record) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(YhUserTemperatureApp record) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer tid) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.deleteByPrimaryKey(tid);
	}

	@Override
	public YhUserTemperatureApp selectByPrimaryKey(Integer tid) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.selectByPrimaryKey(tid);
	}


	@Override
	public List<YhUserTemperatureApp> findTiwenAllInfoByUserCode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserTemperatureMapper.findTiwenAllInfoByUserCode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserTemperatureApp> findTemperatureByYear(String time, String usercode, Integer pageNum, Integer pageSize) {
		List<YhUserTemperatureApp> list = yhUserTemperatureMapper.findTemperatureByYear(time, usercode,pageNum,pageSize);
		return list;
	}

	@Override
	public List<YhUserTemperatureApp> findTemperatureByMonth(String month, String usercode, Integer pageNum, Integer pageSize) {
		List<YhUserTemperatureApp> list = yhUserTemperatureMapper.findTemperatureByMonth(month, usercode,pageNum,pageSize);
		return list;
	}

	@Override
	public List<YhUserTemperatureApp> findTemperatureByWeek(String strTime, String endTime, String usercode, Integer pageNum, Integer pageSize) {
		List<YhUserTemperatureApp> list = yhUserTemperatureMapper.findTemperatureByWeek(strTime, endTime, usercode,pageNum,pageSize);
		return list;
	}

	@Override
	public int findTemperatureByDiRe(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.findTemperatureByDiRe(usercode,timetype,strTime,endTime,page);
	}

	@Override
	public int findTemperatureByGaoRe(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.findTemperatureByGaoRe(usercode,timetype,strTime,endTime,page);
	}

	@Override
	public int findTemperatureByZC(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.findTemperatureByZC(usercode,timetype,strTime,endTime,page);
	}

	@Override
	public int findTemperatureByGD(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		// TODO Auto-generated method stub
		return yhUserTemperatureMapper.findTemperatureByGD(usercode,timetype,strTime,endTime,page);
	}

	@Override
	public List<Integer> findtemperatureTimeFenBuYear(String time, String usercode, Integer page) {
		return yhUserTemperatureMapper.findtemperatureTimeFenBuYear(time,usercode,page);
	}

	@Override
	public List<Integer> findtemperatureTimeFenBuMonth(String month, String usercode, Integer page) {
		return yhUserTemperatureMapper.findtemperatureTimeFenBuMonth(month,usercode,page);
	}

	@Override
	public List<Integer> findtemperatureTimeFenBuWeek(String strTime, String endTime, String usercode, Integer page) {
		return yhUserTemperatureMapper.findtemperatureTimeFenBuWeek(strTime,endTime,usercode,page);
	}

}