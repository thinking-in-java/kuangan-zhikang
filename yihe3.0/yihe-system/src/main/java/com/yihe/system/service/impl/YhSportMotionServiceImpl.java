/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.mapper.YhSportMotionMapper;
import com.yihe.system.service.YhSportMotionService;

/**
 * @author dong
 *
 */
@Service
@Transactional
public class YhSportMotionServiceImpl implements YhSportMotionService{

	@Autowired
	YhSportMotionMapper yhSportMotionMapper;

	@Override
	public List<YhSportMotion> selectSport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.selectSport(map);
	}

	@Override
	public int selectSportConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.selectSportConut(map);
	}

	@Override
	public int insertSelective(YhSportMotion record) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhSportMotion record) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public YhSportMotion selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int selctSportNmaeRepetition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.selctSportNmaeRepetition(map);
	}
	
	@Override
	public List<YhSportMotion> selectByname(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.selectByname(map);
	}

	@Override
	public List<YhSportMotion> selectByhot(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.selectByhot(map);
	}

	@Override
	public List<YhSportMotion> selectByhotCollect(String userCode) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.selectByhotCollect(userCode);
	}

	@Override
	public YhSportMotion sportShowName(String sportName) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.sportShowName(sportName);
	}

	@Override
	public List<YhSportMotion> selectNewestTendata() {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.selectNewestTendata();
	}

	@Override
	public List<YhSportMotion> selectSportNameByParam(String sportName) {
		// TODO Auto-generated method stub
		return yhSportMotionMapper.selectSportNameByParam(sportName);
	}
	
}
