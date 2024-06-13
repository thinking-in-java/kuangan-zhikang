package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserOxygen;
import com.yihe.system.mapper.YHUserOxygenMapper;
import com.yihe.system.service.YHUserOxygenService;

@Service
public class YHUserOxygenServiceImpl implements YHUserOxygenService {
	@Autowired
	YHUserOxygenMapper yHUserOxygenMapper;

	@Override
	public int deleteByPrimaryKey(Integer uOxygenId) {
		// TODO Auto-generated method stub
		return yHUserOxygenMapper.deleteByPrimaryKey(uOxygenId);
	}

	@Override
	public int insert(YHUserOxygen record) {
		// TODO Auto-generated method stub
		return yHUserOxygenMapper.insert(record);
	}

	@Override
	public int insertSelective(YHUserOxygen record) {
		// TODO Auto-generated method stub
		return yHUserOxygenMapper.insertSelective(record);
	}

	@Override
	public YHUserOxygen selectByPrimaryKey(Integer uOxygenId) {
		// TODO Auto-generated method stub
		return yHUserOxygenMapper.selectByPrimaryKey(uOxygenId);
	}

	@Override
	public YHUserOxygen selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return yHUserOxygenMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public int updateByPrimaryKeySelective(YHUserOxygen record) {
		// TODO Auto-generated method stub
		return yHUserOxygenMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YHUserOxygen record) {
		// TODO Auto-generated method stub
		return yHUserOxygenMapper.updateByPrimaryKey(record);
	}

	@Override
	public Map<String, Object> selectYHUserOxygen(String userCode) {
		// TODO Auto-generated method stub
		return yHUserOxygenMapper.selectYHUserOxygen(userCode);
	}

	@Override
	public PageInfo<YHUserOxygen> queryUserByCriteriaWithPage(String userCode, int page, int limit) {
		// TODO Auto-generated method stub
		  //设置分页参数
        PageHelper.startPage(page,limit);
		List<YHUserOxygen> oxygenList = yHUserOxygenMapper.selectPageByParam(userCode);
		return new PageInfo<YHUserOxygen>(oxygenList);
	}

	@Override
	public PageInfo<YHUserOxygen> selectYHUserOxygen2(String userCode, int page, int limit) {
		  //设置分页参数
        PageHelper.startPage(page,limit);
		List<YHUserOxygen> list = yHUserOxygenMapper.selectPageYHUserOxygen(userCode);
		return new PageInfo<YHUserOxygen>(list);
	}

	@Override
	public PageInfo<YHUserOxygen> queryUserHeartByCriteriaWithPage(String userCode, int page, int limit) {
		// TODO Auto-generated method stub
		  //设置分页参数
      PageHelper.startPage(page,limit);
		List<YHUserOxygen> heartList = yHUserOxygenMapper.selectPageByheart(userCode);
		return new PageInfo<YHUserOxygen>(heartList);
	}

	@Override
	public PageInfo<YHUserOxygen> selectYHUserHeart(String userCode, int page, int limit) {
		  //设置分页参数
        PageHelper.startPage(page,limit);
		List<YHUserOxygen> list = yHUserOxygenMapper.selectPageYHUserHeart(userCode);
		return new PageInfo<YHUserOxygen>(list);
	}

	

}
