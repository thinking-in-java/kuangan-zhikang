package com.yihe.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhHeThresholdAlarm;
import com.yihe.system.domain.YhHeThresholdAlarmVOJO;
import com.yihe.system.mapper.YhHeThresholdAlarmMapper;
import com.yihe.system.service.YhHeThresholdAlarmService;

/**
* YhHeThresholdAlarmService实现
* Created by royiwen on 2020/3/27.
*/
@Service
public class YhHeThresholdAlarmServiceImpl  implements YhHeThresholdAlarmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHeThresholdAlarmServiceImpl.class);

    @Autowired
    YhHeThresholdAlarmMapper yhHeThresholdAlarmMapper;

	@Override
	public PageInfo<YhHeThresholdAlarmVOJO> findYhHeThresholdAlarmByDoctor(String usercode,String nameLike, String nametype,
			String status, String strTime, String endTime, int pageNum, int pageSize, Integer alertId) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<YhHeThresholdAlarmVOJO> list = yhHeThresholdAlarmMapper.findYhHeThresholdAlarmByDoctor(usercode,nameLike, nametype, status, strTime, endTime, alertId);
		return new PageInfo<>(list);
	}

	@Override
	public List<YhHeThresholdAlarmVOJO> findYhHeThresholdTypeByDoctor(String usercode) {
		// TODO Auto-generated method stub
		return yhHeThresholdAlarmMapper.findYhHeThresholdTypeByDoctor(usercode);
	}

	@Override
	public List<YhHeThresholdAlarmVOJO> findYhHeThresholdTypeByDoctorAndStatus(String usercode) {
		// TODO Auto-generated method stub
		return yhHeThresholdAlarmMapper.findYhHeThresholdTypeByDoctorAndStatus(usercode);
	}

	@Override
	public YhHeThresholdAlarm selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return yhHeThresholdAlarmMapper.selectByPrimaryKey(id);
	}

	@Override
	public YhHeThresholdAlarmVOJO findYhHeThresholdTypeByIdInfo(String id) {
		// TODO Auto-generated method stub
		return yhHeThresholdAlarmMapper.findYhHeThresholdTypeByIdInfo(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhHeThresholdAlarm record) {
		// TODO Auto-generated method stub
		return yhHeThresholdAlarmMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeySelectiveBYVOJO(YhHeThresholdAlarmVOJO vojo) {
		// TODO Auto-generated method stub
		return yhHeThresholdAlarmMapper.updateByPrimaryKeySelectiveBYVOJO(vojo);
	}

}