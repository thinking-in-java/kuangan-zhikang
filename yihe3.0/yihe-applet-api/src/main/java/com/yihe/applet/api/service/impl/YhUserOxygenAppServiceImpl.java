package com.yihe.applet.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserOxygenApp;
import com.yihe.applet.api.mapper.YhUserOxygenAppMapper;
import com.yihe.applet.api.service.YhUserOxygenAppService;
 

/**
 * YhUserOxygenService实现 Created by dyb on 2020/3/3.
 */
@Service
public class YhUserOxygenAppServiceImpl  
		implements YhUserOxygenAppService {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserOxygenAppServiceImpl.class);

	@Autowired
	YhUserOxygenAppMapper yhUserOxygenMapper;

	@Override
	public YhUserOxygenApp selectByPrimaryKeyBytime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.selectByPrimaryKeyBytime(E_User_Id);
	}

	@Override
	public List<YhUserOxygenApp> selectByPrimaryKeyAllInfo(Integer E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.selectByPrimaryKeyAllInfo(E_User_Id);
	}

	@Override
	public List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public List<YhUserOxygenApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public List<YhUserOxygenApp> findUserOxyGenInfoByeidLimit7Order(String eid) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.findUserOxyGenInfoByeidLimit7Order(eid);
	}

	@Override
	public YhUserOxygenApp selectByPrimaryKey(Integer uOxygenId) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.selectByPrimaryKey(uOxygenId);
	}

	@Override
	public int insertSelective(YhUserOxygenApp record) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserOxygenApp record) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer uOxygenId) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.deleteByPrimaryKey(uOxygenId);
	}

	@Override
	public List<Integer> findtemperatureTimeFenBu(String usercode) {
		// TODO Auto-generated method stub
		return yhUserOxygenMapper.findtemperatureTimeFenBu(usercode);
	}

	@Override
	public List<YhUserOxygenApp> findOxygenAllByUserCode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserOxygenMapper.findOxygenAllByUserCode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserOxygenApp> findOxygenByYear(String time, String usercode, Integer page, Integer pagesize) {
		return yhUserOxygenMapper.findOxygenByYear(time,usercode,page,pagesize);
	}

	@Override
	public List<YhUserOxygenApp> findOxygenByMonth(String month, String usercode, Integer page, Integer pagesize) {
		return yhUserOxygenMapper.findOxygenByMonth(month,usercode,page,pagesize);
	}

	@Override
	public List<YhUserOxygenApp> findOxygenByWeek(String strTime, String endTime, String usercode, Integer page, Integer pagesize) {
		return yhUserOxygenMapper.findOxygenByWeek(strTime,endTime,usercode,page,pagesize);
	}

	@Override
	public List<Integer> findOxygenTimeFenBuYear(String time, String usercode, Integer page) {
		return yhUserOxygenMapper.findOxygenTimeFenBuYear(time,usercode,page);
	}

	@Override
	public List<Integer> findOxygenTimeFenBuMonth(String month, String usercode, Integer page) {
		return yhUserOxygenMapper.findOxygenTimeFenBuMonth(month, usercode, page);
	}

	@Override
	public List<Integer> findOxygenTimeFenBuWeek(String strTime, String endTime, String usercode, Integer page) {
		return yhUserOxygenMapper.findOxygenTimeFenBuWeek(strTime, endTime, usercode, page);
	}

	@Override
	public int findUserOxygenByZhengchang(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		// TODO 自动生成的方法存根
		return yhUserOxygenMapper.findUserOxygenByZhengchang(usercode, timetype, strTime, endTime, page);
	}

	@Override
	public int findUserOxygenByYichang(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		// TODO 自动生成的方法存根
		return yhUserOxygenMapper.findUserOxygenByYichang(usercode, timetype, strTime, endTime, page);
	}

}