/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserFoodGuide;
import com.yihe.system.mapper.YhUserFoodGuideMapper;
import com.yihe.system.service.YhUserFoodGuideService;

/**
 * @author dong
 *
 */
@Service
public class YhUserFoodGuideServiceImpl implements YhUserFoodGuideService{

	 @Autowired
	 YhUserFoodGuideMapper yhUserFoodGuideMapper;

	 
	@Override
	public List<YhUserFoodGuide> selectAnalyze(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhUserFoodGuideMapper.selectAnalyze(map);
	}

	@Override
	public int selectAnalyzeConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhUserFoodGuideMapper.selectAnalyzeConut(map);
	}

	@Override
	public YhUserFoodGuide selectByPrimaryKey(Integer foodguideId) {
		// TODO Auto-generated method stub
		return yhUserFoodGuideMapper.selectByPrimaryKey(foodguideId);
	}

	@Override
	public YhUserFoodGuide findUserFoodGuiDeByid(Integer id) {
		// TODO Auto-generated method stub
		return yhUserFoodGuideMapper.findUserFoodGuiDeByid(id);
	}

}
