package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.TestingVOJO;
import com.yihe.system.domain.YHUserTemperature;
import com.yihe.system.mapper.YHUserTemperatureMapper;
import com.yihe.system.service.YHUserTemperatureService;

@Service
public class YHUserTemperatureServiceImpl implements YHUserTemperatureService {
	@Autowired
	YHUserTemperatureMapper yHUserTemperatureMapper;

	@Override
	public int deleteByPrimaryKey(Integer tid) {
		// TODO Auto-generated method stub
		return yHUserTemperatureMapper.deleteByPrimaryKey(tid);
	}

	@Override
	public int insert(YHUserTemperature record) {
		// TODO Auto-generated method stub
		return yHUserTemperatureMapper.insert(record);
	}

	@Override
	public int insertSelective(YHUserTemperature record) {
		// TODO Auto-generated method stub
		return yHUserTemperatureMapper.insertSelective(record);
	}

	@Override
	public YHUserTemperature selectByPrimaryKey(Integer tid) {
		// TODO Auto-generated method stub
		return yHUserTemperatureMapper.selectByPrimaryKey(tid);
	}

	@Override
	public int updateByPrimaryKeySelective(YHUserTemperature record) {
		// TODO Auto-generated method stub
		return yHUserTemperatureMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YHUserTemperature record) {
		// TODO Auto-generated method stub
		return yHUserTemperatureMapper.updateByPrimaryKey(record);
	}

	@Override
	public YHUserTemperature selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return yHUserTemperatureMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public List<YHUserTemperature> selectByPrimaryKeyForInfoNum(String usercode, String nameLike, String username,
			String endTime, String strTime) {
		// TODO Auto-generated method stub
		return yHUserTemperatureMapper.selectByPrimaryKeyForInfoNum(usercode, nameLike, username, endTime, strTime);
	}

	@Override
	public PageInfo<TestingVOJO> selectByPrimaryKeyForInfoName(String usercode, String nameLike, String usernameLike,
			String endTime, String strTime, String TypeName,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<TestingVOJO> selectByPrimaryKeyForInfoName = yHUserTemperatureMapper.selectByPrimaryKeyForInfoName(usercode, nameLike, usernameLike, endTime, strTime, TypeName);
		return new PageInfo<>(selectByPrimaryKeyForInfoName);
	}
	
	

	@Override
	public Map<String, Object> selectUserAnimalHeat (String userCode) {
		return yHUserTemperatureMapper.selectUserAnimalHeat(userCode);
	}

	@Override
	public PageInfo<YHUserTemperature> queryUserByCriteriaWithPage(String userCode, int page,int limit) {
		//设置分页参数
        PageHelper.startPage(page,limit);
		 List<YHUserTemperature> temperatureList = yHUserTemperatureMapper.selectPageByParam(userCode);
		 return new PageInfo<YHUserTemperature>(temperatureList);
	}

	@Override
	public PageInfo<YHUserTemperature> selectTemperature(String userCode,int page,int limit) {
		// TODO Auto-generated method stub
		//设置分页参数
        PageHelper.startPage(page,limit);
        List<YHUserTemperature> list = yHUserTemperatureMapper.selectTemperature(userCode);
		return new PageInfo<YHUserTemperature>(list);
	}

	@Override
	public List<TestingVOJO> selectByPrimaryKeyForInfoName2(String usercode, String nameLike, String usernameLike,
			String endTime, String strTime, String TypeName,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		return yHUserTemperatureMapper.selectByPrimaryKeyForInfoName2(usercode, nameLike, usernameLike, endTime, strTime, TypeName,pageNum,pageSize);
	}

	@Override
	public PageInfo<TestingVOJO> selectByPrimaryKeyForInfoNameTiwen(String usercode, String nameLike,
			String usernameLike, String endTime, String strTime, String TypeName, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		   PageHelper.startPage(pageNum,pageSize);
	        List<TestingVOJO> list = yHUserTemperatureMapper.selectByPrimaryKeyForInfoNameTiwen(usercode, nameLike, usernameLike, endTime, strTime, TypeName);
			return new PageInfo<TestingVOJO>(list);
	}

}
