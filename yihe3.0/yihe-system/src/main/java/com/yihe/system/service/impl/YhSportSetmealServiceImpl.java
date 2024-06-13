/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhSportSetmeal;
import com.yihe.system.mapper.YhSportSetmealMapper;
import com.yihe.system.service.YhSportSetmealService;

/**
 * @author dong
 *
 */
@Service
@Transactional
public class YhSportSetmealServiceImpl implements YhSportSetmealService{
	@Autowired
	YhSportSetmealMapper yhSportSetmealMapper;

	@Override
	public List<YhSportSetmeal> selectSetmeal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportSetmealMapper.selectSetmeal(map);
	}

	@Override
	public int selectSetmealConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportSetmealMapper.selectSetmealConut(map);
	}
	
	@Override
	public YhSportSetmeal selectYhSportSetmeal(YhSportSetmeal yhSportSetmeal) {
		// TODO Auto-generated method stub
		return yhSportSetmealMapper.selectYhSportSetmeal(yhSportSetmeal);
	}

	@Override
	public YhSportSetmeal selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhSportSetmealMapper.selectByPrimaryKey(id);
	}
}
