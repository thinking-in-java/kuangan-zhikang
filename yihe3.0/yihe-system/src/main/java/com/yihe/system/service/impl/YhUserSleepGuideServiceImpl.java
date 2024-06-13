/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserSleepGuide;
import com.yihe.system.mapper.YhUserSleepGuideMapper;
import com.yihe.system.service.YhUserSleepGuideService;

/**
 * @author dong
 *
 */
@Service
@Transactional
public class YhUserSleepGuideServiceImpl implements YhUserSleepGuideService{
	
	@Autowired
	YhUserSleepGuideMapper yhUserSleepGuideMapper;

	@Override
	public List<YhUserSleepGuide> selectAnalyzeSleep(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhUserSleepGuideMapper.selectAnalyzeSleep(map);
	}

	@Override
	public int selectAnalyzeSleepConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhUserSleepGuideMapper.selectAnalyzeSleepConut(map);
	}

	@Override
	public YhUserSleepGuide selectByPrimaryKey(Integer sleepGuideId) {
		// TODO Auto-generated method stub
		return yhUserSleepGuideMapper.selectByPrimaryKey(sleepGuideId);
	}
}
