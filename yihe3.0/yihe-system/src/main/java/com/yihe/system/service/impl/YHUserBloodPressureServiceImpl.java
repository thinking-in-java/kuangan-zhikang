package com.yihe.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBloodPressure;
import com.yihe.system.mapper.YHUserBloodPressureMapper;
import com.yihe.system.service.YHUserBloodPressureService;
@Service
public class YHUserBloodPressureServiceImpl implements YHUserBloodPressureService {

	 
	 @Autowired
	 YHUserBloodPressureMapper YHUserBloodPressureMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer uBloodpressureId) {
		// TODO Auto-generated method stub
		return YHUserBloodPressureMapper.deleteByPrimaryKey(uBloodpressureId);
	}

	@Override
	public int insert(YHUserBloodPressure record) {
		// TODO Auto-generated method stub
		return YHUserBloodPressureMapper.insert(record);
	}

	@Override
	public int insertSelective(YHUserBloodPressure record) {
		// TODO Auto-generated method stub
		return YHUserBloodPressureMapper.insertSelective(record);
	}

	@Override
	public YHUserBloodPressure selectByPrimaryKey(Integer uBloodpressureId) {
		// TODO Auto-generated method stub
		return YHUserBloodPressureMapper.selectByPrimaryKey(uBloodpressureId);
	}

	@Override
	public int updateByPrimaryKeySelective(YHUserBloodPressure record) {
		// TODO Auto-generated method stub
		return YHUserBloodPressureMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YHUserBloodPressure record) {
		// TODO Auto-generated method stub
		return YHUserBloodPressureMapper.updateByPrimaryKey(record);
	}

	@Override
	public String selectByPrimaryKeyByeidAndOrderby(String usercode) {
		// TODO Auto-generated method stub
		return YHUserBloodPressureMapper.selectByPrimaryKeyByeidAndOrderby(usercode);
	}

	@Override
	public YHUserBloodPressure selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserBloodPressureMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectYHUserBloodPressure(String userCode) {
		// TODO Auto-generated method stub
		return YHUserBloodPressureMapper.selectYHUserBloodPressure(userCode);
	}

	@Override
	public PageInfo<YHUserBloodPressure> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
		   //设置分页参数
        PageHelper.startPage(page,limit);
            List<YHUserBloodPressure> pressureList = YHUserBloodPressureMapper.selectPageByParam(userCode);
            return new PageInfo<YHUserBloodPressure>(pressureList);
	}

	@Override
	public PageInfo<YHUserBloodPressure> selectPressure(Map<String, Object> map,int page,int limit) {
	     //设置分页参数
        PageHelper.startPage(page,limit);
        List<YHUserBloodPressure> list = YHUserBloodPressureMapper.selectPressure(map);
           return new PageInfo<YHUserBloodPressure>(list);
	}

}
