/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserSportsGuide;
import com.yihe.system.mapper.YhUserSportsGuideMapper;
import com.yihe.system.service.YhUserSportsGuideService;

/**
 * @author dong
 *
 */

@Service
@Transactional
public class YhUserSportsGuideServiceImpl implements YhUserSportsGuideService{

	@Autowired
	YhUserSportsGuideMapper YhUserSportsGuideMapper;

	@Override
	public List<YhUserSportsGuide> selectAnalyzeSports(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return YhUserSportsGuideMapper.selectAnalyzeSports(map);
	}

	@Override
	public int selectAnalyzeSportsConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return YhUserSportsGuideMapper.selectAnalyzeSportsConut(map);
	}

	@Override
	public YhUserSportsGuide selectByPrimaryKey(Integer sportsGuideId) {
		// TODO Auto-generated method stub
		return YhUserSportsGuideMapper.selectByPrimaryKey(sportsGuideId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserSportsGuide record) {
		// TODO Auto-generated method stub
		return YhUserSportsGuideMapper.updateByPrimaryKeySelective(record);
	}
}
