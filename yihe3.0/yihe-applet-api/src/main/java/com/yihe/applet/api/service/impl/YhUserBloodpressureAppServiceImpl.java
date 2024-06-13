package com.yihe.applet.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.applet.api.domain.YhUserBloodpressureApp;
import com.yihe.applet.api.mapper.YhUserBloodpressureAppMapper;
import com.yihe.applet.api.service.YhUserBloodpressureAppService;
 
/**
 * 血压实现 Created by dyb on 2019/12/19.
 */
@Service
public class YhUserBloodpressureAppServiceImpl
		 
		implements YhUserBloodpressureAppService {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserBloodpressureAppServiceImpl.class);

	@Autowired
	YhUserBloodpressureAppMapper yhUserBloodpressureMapper;

	@Override
	public List<YhUserBloodpressureApp> selectByPrimaryKeyByEidLimit7(String eid) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKeyByEidLimit7(eid);
	}

	@Override
	public List<YhUserBloodpressureApp> selectByPrimaryKeyBYeid(String eid, String type) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKeyBYeid(eid, type);
	}

	@Override
	public List<YhUserBloodpressureApp> selectByPrimaryKeyByMonthInfo(String eid, String shijian) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKeyByMonthInfo(eid, shijian);
	}

	@Override
	public YhUserBloodpressureApp selectByPrimaryKeyNewest(String eid) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKeyNewest(eid);
	}

	@Override
	public List<String> selectByPrimaryKeyFindEntryTime(String eid) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKeyFindEntryTime(eid);
	}

	@Override
	public List<YhUserBloodpressureApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public YhUserBloodpressureApp selectByPrimaryKeyNewestXinLV(String eid) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKeyNewestXinLV(eid);
	}

	@Override
	public List<YhUserBloodpressureApp> selectByPrimaryKeyBYeidXinlv(String eid) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKeyBYeidXinlv(eid);
	}

	@Override
	public List<YhUserBloodpressureApp> selectByPrimaryKeyFindEntryTimeAnduseridXinlv(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKeyFindEntryTimeAnduseridXinlv(time, usercode);
	}

	@Override
	public YhUserBloodpressureApp selectByPrimaryKey(Integer uBloodpressureId) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.selectByPrimaryKey(uBloodpressureId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserBloodpressureApp record) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(YhUserBloodpressureApp record) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.insertSelective(record);
	}

	@Override
	public List<YhUserBloodpressureApp> findBloodPressureByStrTimeAndEndTime(String usercode,String strTime, String endTime) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.findBloodPressureByStrTimeAndEndTime(usercode, strTime, endTime);
	}

	@Override
	public List<Integer> findBloodPressureTimeFenBu(String usercode) {
		// TODO Auto-generated method stub
		return yhUserBloodpressureMapper.findBloodPressureTimeFenBu(usercode);
	}

	@Override
	public List<YhUserBloodpressureApp> findUserBloodPressureAllInfoByTime(String usercode, Integer timetype, String strTime, String endTime, Integer page, String type) {
		return yhUserBloodpressureMapper.findUserBloodPressureAllInfoByTime(usercode, timetype, strTime, endTime, page, type);
	}

	@Override
	public List<YhUserBloodpressureApp> findBloodpressureAllByUserCode(String usercode,Integer timetype,String strTime,String endTime,String type) {
		return yhUserBloodpressureMapper.findBloodpressureAllByUserCode(usercode, timetype, strTime, endTime, type);
	}

	@Override
	public List<Integer> findBloodPressureTimeFenBuYear(String time, String usercode, Integer page,String type) {
		return yhUserBloodpressureMapper.findBloodPressureTimeFenBuYear(time,usercode,page,type);
	}

	@Override
	public List<Integer> findBloodPressureTimeFenBuMonth(String month, String usercode, Integer page,String type) {
		return yhUserBloodpressureMapper.findBloodPressureTimeFenBuMonth(month,usercode,page,type);
	}

	@Override
	public List<Integer> findBloodPressureTimeFenBuWeek(String strTime, String endTime, String usercode, Integer page,String type) {
		return yhUserBloodpressureMapper.findBloodPressureTimeFenBuWeek(strTime,endTime,usercode,page,type);
	}

	@Override
	public List<YhUserBloodpressureApp> findBloodpressureByYear(String time, String usercode, Integer page, Integer pagesize,String type) {
		return yhUserBloodpressureMapper.findBloodpressureByYear(time,usercode,page,pagesize,type);
	}

	@Override
	public List<YhUserBloodpressureApp> findBloodpressureByMonth(String month, String usercode, Integer page, Integer pagesize,String type) {
		return yhUserBloodpressureMapper.findBloodpressureByMonth(month,usercode,page,pagesize,type);
	}

	@Override
	public List<YhUserBloodpressureApp> findBloodpressureByWeek(String strTime, String endTime, String usercode, Integer page, Integer pagesize,String type) {
		return yhUserBloodpressureMapper.findBloodpressureByWeek(strTime,endTime,usercode,page,pagesize,type);
	}

	@Override
	public int findUserBloodPressurePianDi(String usercode,Integer timetype,String strTime,String endTime,Integer page,String type) {
		// TODO 自动生成的方法存根
		return yhUserBloodpressureMapper.findUserBloodPressurePianDi(usercode,timetype,strTime,endTime,page,type);
	}

	@Override
	public int findUserBloodPressureZC(String usercode,Integer timetype,String strTime,String endTime,Integer page,String type) {
		// TODO 自动生成的方法存根
		return yhUserBloodpressureMapper.findUserBloodPressureZC(usercode,timetype,strTime,endTime,page,type);
	}

	@Override
	public int findUserBloodPressurepiangao(String usercode,Integer timetype,String strTime,String endTime,Integer page,String type) {
		// TODO 自动生成的方法存根
		return yhUserBloodpressureMapper.findUserBloodPressurepiangao(usercode,timetype,strTime,endTime,page,type);
	}

	@Override
	public int findUserBloodPressureqingdu(String usercode,Integer timetype,String strTime,String endTime,Integer page,String type) {
		// TODO 自动生成的方法存根
		return yhUserBloodpressureMapper.findUserBloodPressureqingdu(usercode,timetype,strTime,endTime,page,type);
	}

	@Override
	public int findUserBloodPressurezhongdu(String usercode,Integer timetype,String strTime,String endTime,Integer page,String type) {
		// TODO 自动生成的方法存根
		return yhUserBloodpressureMapper.findUserBloodPressurezhongdu(usercode,timetype,strTime,endTime,page,type);
	}

	@Override
	public int findUserBloodPressurezhongdu2(String usercode,Integer timetype,String strTime,String endTime,Integer page,String type) {
		// TODO 自动生成的方法存根
		return yhUserBloodpressureMapper.findUserBloodPressurezhongdu2(usercode,timetype,strTime,endTime,page,type);
	}

	@Override
	public int findUserBloodPressuredanchun(String usercode,Integer timetype,String strTime,String endTime,Integer page,String type) {
		// TODO 自动生成的方法存根
		return yhUserBloodpressureMapper.findUserBloodPressuredanchun(usercode,timetype,strTime,endTime,page,type);
	}

}