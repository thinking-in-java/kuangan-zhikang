package com.yihe.applet.api.service.impl;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserBmiApp;
import com.yihe.applet.api.mapper.YhUserBmiAppMapper;
import com.yihe.applet.api.service.YhUserBmiAppService;

/**
* YhUserBmiService实现
* Created by dyb on 2019/12/19.
*/
@Service
public class YhUserBmiAppServiceImpl  implements YhUserBmiAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserBmiAppServiceImpl.class);

    @Autowired
    YhUserBmiAppMapper yhUserBmiMapper;

	@Override
	public List<YhUserBmiApp> selectByPrimaryKeyByUserIdLimit7(String eid) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.selectByPrimaryKeyByUserIdLimit7(eid);
	}

	@Override
	public List<YhUserBmiApp> selectByPrimaryKeyByMonthInfo(String eid, String shijian) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.selectByPrimaryKeyByMonthInfo(eid, shijian);
	}

	@Override
	public List<YhUserBmiApp> selectByPrimaryKeyBYeid(String usercode,Integer timetype,String strTime,String endTime,Integer page) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.selectByPrimaryKeyBYeid(usercode,timetype,strTime,endTime,page);
	}

	@Override
	public YhUserBmiApp selectByPrimaryKeyNewest(String eid) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.selectByPrimaryKeyNewest(eid);
	}

	 

	@Override
	public List<YhUserBmiApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public List<String> selectByPrimaryKeyFindEntryTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public int updateByExampleSelective2(Integer uBmiId) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.updateByExampleSelective2(uBmiId);
	}

	@Override
	public YhUserBmiApp selectByPrimaryKey(Integer uBmiId) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.selectByPrimaryKey(uBmiId);
	}

	@Override
	public int insertSelective(YhUserBmiApp record) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.insertSelective(record);
	}

	@Override
	public int updateByExampleSelective(YhUserBmiApp record) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.updateByExampleSelective(record);
	}

	@Override
	public List<Integer> findtemperatureTimeFenBu(String usercode) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.findtemperatureTimeFenBu(usercode);
	}

	@Override
	public List<YhUserBmiApp> findBmiInfoAllByUsercode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserBmiMapper.findBmiInfoAllByUsercode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserBmiApp> findWeightByYear(String time, String usercode, Integer page, Integer pagesize) {
		return yhUserBmiMapper.findWeightByYear(time,usercode,page,pagesize);
	}

	@Override
	public List<YhUserBmiApp> findWeightByMonth(String month, String usercode, Integer page, Integer pagesize) {
		return yhUserBmiMapper.findWeightByMonth(month,usercode,page,pagesize);
	}

	@Override
	public List<YhUserBmiApp> findWeightByWeek(String strTime, String endTime, String usercode, Integer page, Integer pagesize) {
		return yhUserBmiMapper.findWeightByWeek(strTime,endTime,usercode,page,pagesize);
	}

	@Override
	public List<Integer> findWeightTimeFenBuYear(String time, String usercode, Integer page) {
		return yhUserBmiMapper.findWeightTimeFenBuYear(time,usercode,page);
	}

	@Override
	public List<Integer> findWeightTimeFenBuMonth(String month, String usercode, Integer page) {
		return yhUserBmiMapper.findWeightTimeFenBuMonth(month,usercode,page);
	}

	@Override
	public List<Integer> findWeightTimeFenBuWeek(String strTime, String endTime, String usercode, Integer page) {
		return yhUserBmiMapper.findWeightTimeFenBuWeek(strTime,endTime,usercode,page);
	}

	@Override
	public List<YhUserBmiApp> findUserBmiByUserCodeAll(String usercode) {
		// TODO Auto-generated method stub
		return yhUserBmiMapper.findUserBmiByUserCodeAll(usercode);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserBmiApp record) {
		// TODO 自动生成的方法存根
		return yhUserBmiMapper.updateByPrimaryKeySelective(record);
	}

}