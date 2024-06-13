package com.yihe.applet.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserHeartRateApp;
import com.yihe.applet.api.mapper.YhUserHeartRateAppMapper;
import com.yihe.applet.api.service.YhUserHeartRateAppService;


/**
 * YhUserHeartRateService实现 Created by dyb on 2020/3/6.
 */
@Service
public class YhUserHeartRateAppServiceImpl
		implements YhUserHeartRateAppService {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserHeartRateAppServiceImpl.class);

	@Autowired
	YhUserHeartRateAppMapper yhUserHeartRateMapper;

	@Override
	public List<YhUserHeartRateApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserHeartRateMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public YhUserHeartRateApp selectByPrimaryKeyOrderTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserHeartRateMapper.selectByPrimaryKeyOrderTime(E_User_Id);
	}

	@Override
	public List<YhUserHeartRateApp> findUserHeartRateByEidLimit7(String eid) {
		// TODO Auto-generated method stub
		return yhUserHeartRateMapper.findUserHeartRateByEidLimit7(eid);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserHeartRateApp record) {
		// TODO Auto-generated method stub
		return yhUserHeartRateMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public YhUserHeartRateApp selectByPrimaryKey(Integer rid) {
		// TODO Auto-generated method stub
		return yhUserHeartRateMapper.selectByPrimaryKey(rid);
	}

	@Override
	public int deleteByPrimaryKey(Integer rid) {
		// TODO Auto-generated method stub
		return yhUserHeartRateMapper.deleteByPrimaryKey(rid);
	}

	@Override
	public int insertSelective(YhUserHeartRateApp record) {
		// TODO Auto-generated method stub
		return yhUserHeartRateMapper.insertSelective(record);
	}


	@Override
	public List<YhUserHeartRateApp> findHeartRateAllByUserCode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserHeartRateMapper.findHeartRateAllByUserCode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserHeartRateApp> findHeartRateByYear(String time, String usercode, Integer pageNum, Integer pageSize) {
		return yhUserHeartRateMapper.findHeartRateByYear(time, usercode, pageNum, pageSize);
	}

	@Override
	public List<YhUserHeartRateApp> findHeartRateByMonth(String month, String usercode, Integer pageNum, Integer pageSize) {
		return yhUserHeartRateMapper.findHeartRateByMonth(month, usercode, pageNum, pageSize);
	}

	@Override
	public List<YhUserHeartRateApp> findHeartRateByWeek(String strTime, String endTime, String usercode, Integer pageNum, Integer pageSize) {
		return yhUserHeartRateMapper.findHeartRateByWeek(strTime, endTime, usercode, pageNum, pageSize);
	}

	@Override
	public List<Integer> findHeartRateTimeFenBuYear(String time, String usercode, Integer page) {
		return yhUserHeartRateMapper.findHeartRateTimeFenBuYear(time, usercode, page);
	}

	@Override
	public List<Integer> findHeartRateTimeFenBuMonth(String month, String usercode, Integer page) {
		return yhUserHeartRateMapper.findHeartRateTimeFenBuMonth(month, usercode, page);
	}

	@Override
	public List<Integer> findHeartRateTimeFenBuWeek(String strTime, String endTime, String usercode, Integer page) {
		return yhUserHeartRateMapper.findHeartRateTimeFenBuWeek(strTime, endTime, usercode, page);
	}

	@Override
	public List<String> selectByPrimaryKeyFindEntryTime(String usercode) {
		return yhUserHeartRateMapper.selectByPrimaryKeyFindEntryTime(usercode);
	}

	@Override
	public int findHeartRateGuoSu(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		return yhUserHeartRateMapper.findHeartRateGuoSu(usercode, timetype, strTime, endTime, page);
	}

	@Override
	public int findHeartRateGuoHuan(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		return yhUserHeartRateMapper.findHeartRateGuoHuan(usercode, timetype, strTime, endTime, page);
	}

	@Override
	public int findHeartRateZhengChang(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		return yhUserHeartRateMapper.findHeartRateZhengChang(usercode, timetype, strTime, endTime, page);
	}

	@Override
	public List<Integer> findtemperatureTimeFenBu(String usercode) {
		// TODO Auto-generated method stub
		return yhUserHeartRateMapper.findtemperatureTimeFenBu(usercode);
	}

}